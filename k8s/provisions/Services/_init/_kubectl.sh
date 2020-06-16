echo "--- init: [${node_name}] kubectl ---"


if [ "n$_DRY_RUN" = "n" ]; then

cat <<EOF >>"$USER_HOME/.bashrc";

# Enable kubectl autocompletion
source <(kubectl completion bash)

EOF

fi
