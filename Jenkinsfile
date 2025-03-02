pipeline {
    agent any

    environment {
        // Docker Hub credentials (make sure you have configured these in Jenkins credentials)
        DOCKER_CREDENTIALS_ID = 'docker_credential'
        DOCKERHUB_REPO = 'manite'
        IMAGE_VERSION = 's5' // Set the version of the image here
        RECIPIENTS = 'mazen.abono7tah@gmail.com'
        TEMPO_CONFIG = "${WORKSPACE}/docker-compose/observability/tempo/tempo.yml"
        PROMETHEUS_CONFIG = "${WORKSPACE}/docker-compose/observability/prometheus/prometheus.yml"
        SUDO_PASS = "root"
    }



    stages {
   /*   stage('Clean and Test') {
            steps {
                script {
                    // Run mvn clean and test in the root directory
                    sh "mvn clean test"
                }
            }
        }
        stage('Clean and build') {
            steps {
                script {
                    // Run mvn clean and test in the root directory
                    sh "mvn clean install"
                }
            }
        }*/
        stage('Check Docker Platform') {
            steps {
                sh 'docker version'
            }
        }
        stage('Build and Push Service accounts') {
            steps {
                script {
                    dir('accounts') {
                        // Build Docker image and push it to Docker Hub for service1
                        sh "mvn compile jib:dockerBuild -Dimage=${DOCKERHUB_REPO}/accounts-img:${IMAGE_VERSION}"
                    }
                }
            }
        }

        stage('Build and Push Service loans') {
            steps {
                script {
                    dir('loans') {
                        // Build Docker image and push it to Docker Hub for service2
                        sh "mvn compile jib:dockerBuild -Dimage=${DOCKERHUB_REPO}/loans-img:${IMAGE_VERSION}"
                    }
                }
            }
        }

        stage('Build and Push Service cards') {
            steps {
                script {
                    dir('cards') {
                        // Build Docker image and push it to Docker Hub for service3
                        sh "mvn compile jib:dockerBuild -Dimage=${DOCKERHUB_REPO}/cards:${IMAGE_VERSION}"
                    }
                }
            }
        }

        stage('Build and Push Service messages') {
            steps {
                script {
                    dir('message') {
                        // Build Docker image and push it to Docker Hub for service4
                        sh "mvn compile jib:dockerBuild -Dimage=${DOCKERHUB_REPO}/messages-img:${IMAGE_VERSION}"
                    }
                }
            }
        }

        stage('Build and Push Service gatewayserver') {
            steps {
                script {
                    dir('gatewayserver') {
                        // Build Docker image and push it to Docker Hub for service5
                        sh "mvn compile jib:dockerBuild -Dimage=${DOCKERHUB_REPO}/gatewayserver-img:${IMAGE_VERSION}"
                    }
                }
            }
        }

        stage('Build and Push Service eurekaserver') {
            steps {
                script {
                    dir('eurekaserver') {
                        // Build Docker image and push it to Docker Hub for service6
                        sh "mvn compile jib:dockerBuild -Dimage=${DOCKERHUB_REPO}/eurekaserver-img:${IMAGE_VERSION}"
                    }
                }
            }
        }
        stage('Build and Push Service configserver') {
            steps {
                script {
                    dir('configserver') {
                        // Build Docker image and push it to Docker Hub for service6
                        sh "mvn compile jib:dockerBuild -Dimage=${DOCKERHUB_REPO}/configserver-img:${IMAGE_VERSION}"
                    }
                }
            }
        }
       /* stage('Send Email Notification publish') {
            steps {
                mail (
                         to: "${RECIPIENTS}",
                         subject: "Service Publish",
                         body: "Services has been successfully pushed to Docker Hub with tag ${IMAGE_VERSION}."
                     )
            }
        }*/
        stage("Remove old containers ") {
            steps {
                script {
                    sh '''
                        echo "<<<<<<<<<<<<Start remove containers >>>>>>>>>>>>>>>>>"

                                if docker ps -a | grep "ms" | awk '{print $1}' | xargs docker rm -f; then
                                    printf 'Clearing old conatainers succeeded\n'
                                else
                                    printf 'Clearing old conatainers failed\n'
                                fi

                                echo "<<<<<<<<<<<<End remove containers >>>>>>>>>>>>>>>>>"
                    '''
                }
            }
        }
        stage("Remove images") {
            steps {
                script {
                            sh '''
                                echo "<<<<<<<<<<<<Start remove images >>>>>>>>>>>>>>>>>"

                                if docker images -a | grep "img" | awk '{print $1":"$2}' | xargs docker rmi -f; then
                                    printf 'Clearing old images succeeded\n'
                                else
                                    printf 'Clearing old images failed\n'
                                fi

                                echo "<<<<<<<<<<<<End remove images >>>>>>>>>>>>>>>>>"
                            '''
                }
            }
        }



        stage('Deploy with Docker Compose') {
            steps {
                script {
                    // Navigate to the folder containing docker-compose.yml and run Docker Compose to deploy services
                    dir('docker-compose/default') {
                        sh "docker-compose up -d"
                    }
                }
            }
        }
        stage('Send Email Notification deploy') {
                    steps {

                           mail (
                                     to: "${RECIPIENTS}",
                                     subject: "Service deploy",
                                     body: "Services has been successfully deploy to Docker"
                                 )

                    }
                }
    }


    post {
        success {
            echo "Pipeline completed successfully!"
        }
        failure {
            echo "Pipeline failed."
        }
    }
}
