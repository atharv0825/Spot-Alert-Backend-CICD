pipeline {
    agent any

    environment {
            JAVA_HOME = "/usr/lib/jvm/java-21-openjdk-amd64"
            PATH = "${JAVA_HOME}/bin:${env.PATH}"
    }

    stages {
        stage('Checkout Source') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/atharv0825/Spot-Alert-Backend-CICD.git',
                    credentialsId: 'github-creds'
            }
        }

        stage('Verify Java') {
                    steps {
                        sh '''
                            java -version
                            mvn -version
                        '''
                    }
        }

        stage('Build Application'){
            steps{
                sh 'mvn clean package -DskipTests'
            }
        }
    }
}
