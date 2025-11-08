pipeline {
  agent any
  tools {
    maven 'Maven 3.9.9'   // must match the name above
  }
  stages {
    stage('Verify Maven') {
      steps {
        sh 'mvn -v'
      }
    }
    stage('Build & Test') {
      steps {
        sh 'mvn -B -q clean test'
      }
    }
  }
}

