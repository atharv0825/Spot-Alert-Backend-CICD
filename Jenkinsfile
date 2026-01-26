pipeline {
    agent any

    stages {
        stage('Checkout Source') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/atharv0825/Spot-Alert-Backend-CICD.git',
                    credentialsId: 'github-creds'
            }
        }

        stage('Build Application'){
            steps{
                sh 'mvn clean package -DskipTests'
            }
        }
    }
}
