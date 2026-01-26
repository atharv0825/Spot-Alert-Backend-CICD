pipeline {
    agent any

    stages {
        stage('Checkout Source') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/atharv0825/Spot-Alert-Backend-CICD.git',
                    credentialsId: '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'
            }
        }
    }
}
