#! /usr/bin/env bash
# Must be run with 'sudo bash install.sh'
# TODO : give GIT permissions to user root@root
set   -e
shopt -s extglob

export DEBIAN_FRONTEND=noninteractive
readonly PKGS=(build-essential openjdk-7-{jre,jdk} maven mysql-server git)

# Update system and install dependencies.
apt-get -y update
apt-get -y upgrade
apt-get -y install ${PKGS[@]}

# Init service.
sudo cat <<'EOF' > /etc/init.d/spring_example
#! /bin/sh
startme() {
    echo "Starting Spring Example..."
    cd $HOME/Spring-Example/target
    ((nohup java -server -Xmx4048m -jar spring_example-0.1.jar --env="production") & echo $! > /tmp/spring_example.pid &)
}
stopme() {
    echo "Stopping Spring Example..."
    kill -9 $(cat /tmp/spring_example.pid)
}
case "$1" in
    start)
        startme
        ;;
    stop)
        stopme
        ;;
    restart)
        stopme;
        startme
        ;;
    *)
        echo "Usage: $0 {start|stop|restart}"
        exit 1
esac
exit 0
EOF
chown ubuntu:ubuntu /etc/init.d/spring_example
chmod 6775 /etc/init.d/spring_example

# Build
cd $HOME
git clone git@github.com:NelsonRosenbergDev/Spring-Example.git
cd Spring-Example
git checkout master
git pull origin master

# Start service.
service spring_example start
