pipeline {
    environment {
        TELEGRAM_BOT = credentials('TELEGRAM_BOT_ID')
        TELEGRAM_CHAT_ID  = credentials('TELEGRAM_CHAT_ID')
        DOCKERHUB_ID = credentials('DOCKERHUB_ID')
        DOCKERHUB_PASSWORD = credentials('DOCKERHUB_PASSWORD')
    }
    agent {
        docker {
            image 'yaoliinyk/mvn-aws-cli'
            args '-v $HOME/.m2:/root/.m2 -v /var/run/docker.sock:/var/run/docker.sock:rw'
        }
    }


    options {
        buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '10'))
    }

    stages {

        stage('Check versions') {
            parallel {
                stage('mvn version') {
                    steps {
                        sh 'mvn --version'
                    }
                }
                stage('docker version') {
                    steps {
                        sh 'docker --version'
                    }
                }
//                stage('Git checkout') {
//                    steps {
//                        git 'https://github.com/0liinyk/bootiful-playground.git'
//                    }
//                }
            }
        }


        stage('List files') {
            steps {
                sh 'ls -la'
            }
        }

        stage('Unit tests') {
            steps {
                sh 'mvn -B clean test'
            }
        }

        stage('Integration tests') {
            steps {
                sh 'mvn -B failsafe:integration-test'
            }
        }

        stage('Push container to DockerHub') {
            steps {
                sh "mvn -DskipTests clean package  -Djib.to.auth.username=${DOCKERHUB_ID}  -Djib.to.auth.password=${DOCKERHUB_PASSWORD}"
            }
        }

    }
    post {
        always {
            echo "Status of pipeline: ${currentBuild.fullDisplayName} "
            sh "curl -s -X POST https://api.telegram.org/${TELEGRAM_BOT}/sendMessage -d chat_id=${TELEGRAM_CHAT_ID} -d text=\"Job: ${env.BUILD_URL}  has been finished with status: ${currentBuild.currentResult}\" "
        }
    }

}