#!/bin/sh

# installing java 8 and removing java 7
sudo yum install -y java-1.8.0-openjdk-devel
sudo alternatives --set java /usr/lib/jvm/jre-1.8.0-openjdk.x86_64/bin/java
sudo yum remove -y java-1.7.0-openjdk-devel
sudo yum install jq -y

# installing maven
mkdir ~/bin
cd ~/bin
wget https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.6.3/apache-maven-3.6.3-bin.tar.gz
tar -xvzf apache-maven-3.6.3-bin.tar.gz
echo "export PATH=~/bin/apache-maven-3.6.3/bin:${PATH}" >> ~/.bashrc
source ~/.bashrc

# installing docker compose
# sudo curl -kLo ~/bin/docker-compose ${BINARY_PATH_PREFIX}/docker-compose-Linux-x86_64 
# ln -s ../environment/Lab3CodeCommitRepo/docker_compose/docker-compose-Linux-x86_64 docker-compose
# sudo chmod +x ~/bin/docker-compose 

curl -s https://api.github.com/repos/docker/compose/releases/latest | grep browser_download_url  | grep docker-compose-linux-x86_64 | cut -d '"' -f 4 | wget -qi -
chmod +x docker-compose-linux-x86_64
sudo mv docker-compose-linux-x86_64 /usr/local/bin/docker-compose
docker-compose version
sudo usermod -aG docker $USER
newgrp docker

# verifying if everything is properly set
java -version && \
mvn -v &&\
docker-compose -v