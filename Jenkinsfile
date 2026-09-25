pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                echo 'Checking out source code'
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean compile'
            }
        }

        stage('Automated Testing') {
            steps {
                bat 'mvn test'
            }
        }

        stage('Generate Test Report') {
            steps {
                junit 'target/surefire-reports/*.xml'
            }
        }
    }

    post {
        success {
            echo 'All automated tests passed successfully!'
        }

        failure {
            echo 'Automated testing failed. Check the Jenkins test report.'
        }

        always {
            echo 'Continuous Testing execution completed.'
        }
    }
}
