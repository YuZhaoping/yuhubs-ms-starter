echo "--- init: [${node_name}] pki ---"


pki_dest_dir="${K8S_KUBE_PKI_HOME:-/etc/kubernetes/pki}"
pki_dest_dir=$(echo "${_ROOT}$pki_dest_dir" | sed 's/\\//g')


pki_src_dir="$DEPLOY_HOME/pki/etc"

mkdir -p "$pki_dest_dir"
cp -Rf $pki_src_dir/* "$pki_dest_dir/"


sa_dest_dir="$pki_dest_dir/sa"
mkdir -p "$sa_dest_dir"

pki_src_dir="$DEPLOY_HOME/pki/accounts"

service_accounts=("controller-manager" "scheduler")
for sa in ${service_accounts[@]}; do
  sa_src_dir="$pki_src_dir/$sa"

  cp -f $sa_src_dir/* "$sa_dest_dir/"
done


if [ "n$_DRY_RUN" = "n" ]; then
  chown -R $RUN_USER:$RUN_USER "$pki_dest_dir"
fi
