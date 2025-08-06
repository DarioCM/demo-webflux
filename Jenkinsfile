pipeline{

    agent any


    stages{

        stage("SCM checkout"){
            steps{
              checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[credentialsId: 'github-pat-dario', url: 'https://github.com/DarioCM/demo-webflux.git']])
            }
        }

        stage("Build"){
            steps{
                script{
                    sh 'mvn clean install'
                }
            }
        }



    }
}