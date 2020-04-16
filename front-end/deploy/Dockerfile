FROM nginx:alpine

VOLUME ["/etc/nginx", "/usr/share/nginx/html", "/var/log/nginx"]

COPY config/nginx.conf /etc/nginx/nginx.conf
COPY config/default.conf /etc/nginx/conf.d/default.conf
COPY html /usr/share/nginx/html
