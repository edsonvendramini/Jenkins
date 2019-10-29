if (params.Continuar != 'Sim') {
	currentBuild.result = 'UNSTABLE'
} else {
	node {
		def String[] var_arquivarEstag = ['Estagio 1.txt','Estagio 2.txt','Estagio 3.txt']
        def nomeEstagio = ''

        ansiColor('xterm') {
            try {
                nomeEstagio = 'Estagio 1'
                stage(nomeEstagio) {
                    echo 'Iniciando ' + nomeEstagio
                    writeFile file:nomeEstagio + '.txt', text:nomeEstagio, encoding:'UTF-8'
                    echo nomeEstagio + ' Concluido'
                }
                nomeEstagio = 'Estagio 2'
                stage(nomeEstagio) {
                    echo 'Iniciando ' + nomeEstagio
                    writeFile file:nomeEstagio + '.txt', text:nomeEstagio, encoding:'UTF-8'
                    echo nomeEstagio + ' Concluido'
                }
                nomeEstagio = 'Estagio 3'
                stage(nomeEstagio) {
                    echo 'Iniciando ' + nomeEstagio
                    writeFile file:nomeEstagio + '.txt', text:nomeEstagio, encoding:'UTF-8'
                    echo nomeEstagio + ' Concluido'
                }
            } catch (error) {
                echo '\n\n\033[1;31m[Erro]\033[0m Erro no ' + nomeEstagio + '\n\n'
                echo error.toString()
                currentBuild.result = 'FAILURE'
            }
            for (int i = 0; i < var_arquivarEstag.size(); i++) {
                def encontrouEstag = fileExists var_arquivarEstag[i]
                if (encontrouEstag) {
                    archiveArtifacts artifacts: var_arquivarEstag[i]
                }
            }
        }
	}
}
