####
echo "--- init: [${node_name}] kubeconfig ---"


kubeconfig_home="$DEPLOY_HOME/kube"
mkdir -p "$kubeconfig_home"


####

apiserver_address="https://${K8S_APISERVER_VIRTUAL_IP}:${K8S_APISERVER_VIRTUAL_PORT}"

path_to_kubernetes_ca="$DEPLOY_HOME/pki/etc/ca.crt"

account_certs_home="$DEPLOY_HOME/pki/accounts"

kube_cluster_name="${K8S_CLUSTER_NAME:-default-cluster}"
kube_context_name="default-system"


#### admin, controller-manager, scheduler, proxy

config_names=("admin" "controller-manager" "scheduler" "proxy")

for config_name in ${config_names[@]}; do

  config_file_dir="${kubeconfig_home}/${config_name}"
  mkdir -p "$config_file_dir"

  config_filename="${config_file_dir}/${config_name}.conf"

  credential_name="default-${config_name}"

  path_to_key="${account_certs_home}/${config_name}/${config_name}.key"
  path_to_cert="${account_certs_home}/${config_name}/${config_name}.crt"

  if [ ! -f "$config_filename" ]; then
    KUBECONFIG=${config_filename} \
    kubectl config set-cluster ${kube_cluster_name} \
      --server=${apiserver_address} \
      --certificate-authority=${path_to_kubernetes_ca} \
      --embed-certs;

    KUBECONFIG=${config_filename} \
    kubectl config set-credentials ${credential_name} \
      --client-key=${path_to_key} \
      --client-certificate=${path_to_cert} \
      --embed-certs;

    KUBECONFIG=${config_filename} \
    kubectl config set-context ${kube_context_name} \
      --cluster=${kube_cluster_name} \
      --user=${credential_name};

    KUBECONFIG=${config_filename} \
    kubectl config use-context ${kube_context_name};
  fi

done

####
#### Bootstrap token
# https://kubernetes.io/docs/reference/command-line-tools-reference/kubelet-tls-bootstrapping/

credential_name="kubelet-bootstrap"


BOOTSTRAP_TOKEN=$(head -c 16 /dev/urandom | od -An -t x | tr -d ' ')

token_file_dir="${kubeconfig_home}/apiserver"
mkdir -p "$token_file_dir"

token_filename="${token_file_dir}/token.csv"

if [ ! -f "$token_filename" ]; then
cat > "$token_filename" <<EOF
${BOOTSTRAP_TOKEN},${credential_name},10001,"system:bootstrappers"
EOF
else
BOOTSTRAP_TOKEN=$(cat "$token_filename" | awk -F ',' '{print $1}')
fi

echo "BOOTSTRAP_TOKEN=${BOOTSTRAP_TOKEN}"


#### Create script to bind kubelet bootstrap role

script_file="${kubeconfig_home}/admin/bind-bootstrap-role.sh"

if [ ! -f "$script_file" ]; then
cat > "$script_file" <<"EOF"
#!/usr/bin/env bash

cd $(dirname $0)

kubectl create clusterrolebinding kubelet-bootstrap \
  --clusterrole=system:node-bootstrapper \
  --group=system:bootstrappers \
  --kubeconfig=./admin.conf
EOF

chmod a+x "$script_file"
fi


####
#### kubelet

config_name="bootstrap-kubelet"

config_file_dir="${kubeconfig_home}/kubelet"
mkdir -p "$config_file_dir"

config_filename="${config_file_dir}/${config_name}.conf"


if [ ! -f "$config_filename" ]; then

  KUBECONFIG=${config_filename} \
  kubectl config set-cluster ${kube_cluster_name} \
    --server=${apiserver_address} \
    --certificate-authority=${path_to_kubernetes_ca} \
    --embed-certs;

  KUBECONFIG=${config_filename} \
  kubectl config set-credentials ${credential_name} \
    --token=${BOOTSTRAP_TOKEN};

  KUBECONFIG=${config_filename} \
  kubectl config set-context ${kube_context_name} \
    --cluster=${kube_cluster_name} \
    --user=${credential_name};

  KUBECONFIG=${config_filename} \
  kubectl config use-context ${kube_context_name};

fi

####
####

mkdir -p "$USER_HOME/.kube"

cp -vf $kubeconfig_home/admin/admin.conf \
  "$USER_HOME/.kube/config"

if [ "n$_DRY_RUN" = "n" ]; then
  chown -R $LOGIN_USER:$LOGIN_USER "$USER_HOME/.kube"
fi

####
