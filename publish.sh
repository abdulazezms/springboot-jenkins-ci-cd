export PASS=$(sed -n 3p /tmp/books-manager-cred)
export TAG=$(sed -n 2p /tmp/books-manager-cred)
export IMAGE=$(sed -n 1p /tmp/books-manager-cred)
cd ~/deployment-books-manager && docker compose -f docker-compose.prod.yml up -d
