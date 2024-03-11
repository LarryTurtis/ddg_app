#! /bin/sh
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
echo "Provisioning complete"
