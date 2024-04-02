pipeline {
    agent any
    environment {
        registry = "305292172736.dkr.ecr.us-east-1.amazonaws.com/my-docker-repo"
    }
    tools {
        maven 'Maven3'
    }
    stages {
        stage('Cloning Git') {
            steps {
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/Krishnakali-Dutta/docker-spring-boot.git']])
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage('Static Code Analysis') {
            environment {
                SONAR_URL = "http://18.204.7.11:9000"
            }
            steps {
                echo "Running static code analysis..."
                withCredentials([string(credentialsId: 'sonarqube', variable: 'SONAR_AUTH_TOKEN')]) {
                    sh "mvn sonar:sonar -Dsonar.login=$SONAR_AUTH_TOKEN -Dsonar.host.url=${env.SONAR_URL}"
                }
            }
        }
        // Building Docker images
        stage('Building image') {
            steps {
                script {
                    dockerImage = docker.build registry 
                    dockerImage.tag("$BUILD_NUMBER")
                }
            }
        }
        // Uploading Docker images into AWS ECR
        stage('Pushing to ECR') {
            steps {  
                script {
                    sh 'aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 305292172736.dkr.ecr.us-east-1.amazonaws.com'
                    sh 'docker push 305292172736.dkr.ecr.us-east-1.amazonaws.com/my-docker-repo:$BUILD_NUMBER'
                }
            }
        }
        stage('Helm Deploy') {
            steps {
                script {
                    sh "helm upgrade first --install my-april-chart --namespace helm-deployment --set image.tag=$BUILD_NUMBER"
                }
            }
        }
    }
}
