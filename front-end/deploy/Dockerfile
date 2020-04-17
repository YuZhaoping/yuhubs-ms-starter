FROM nginx:alpine

VOLUME ["/etc/nginx", "/usr/share/nginx/html", "/var/log/nginx"]

COPY config /etc/nginx/
COPY html   /usr/share/nginx/html

COPY certs  /etc/nginx/certs

EXPOSE 80 443
