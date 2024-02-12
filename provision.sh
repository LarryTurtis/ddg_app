#! /bin/sh

# Warning! This script does not contain the necessary secrets.
# It is included here for reference only. The actual script will be provided
# by the owner of the repository.

echo "Provisioning virtual machine..."
curl -fsSL https://get.docker.com -o get-docker.sh
sudo sh ./get-docker.sh
sudo apt-get install docker-compose-plugin
sudo usermod -a -G docker $USER
newgrp docker
git clone https://github.com/LarryTurtis/ddg_app.git
cd ddg_app
git pull origin main
docker compose up -d --build
sudo iptables -A PREROUTING -t nat -p tcp --dport 80 -j REDIRECT --to-ports 8080
echo "Provisioning complete"
