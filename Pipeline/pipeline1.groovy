if (params.Continuar != 'Sim') {
	currentBuild.result = 'UNSTABLE'
} else {
	node {
		def String[] var_arquivarEstag = ['Estagio 1.txt','Estagio 2.txt','Estagio 2.1.txt','Estagio 2.2.txt','Estagio 2.3.txt','Estagio 3.txt']
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
                    parallel (
                        (nomeEstagio + '.1'): {
                            writeFile file:nomeEstagio + '.1.txt', text:nomeEstagio + '.1', encoding:'UTF-8'
                        }, (nomeEstagio + '.2'): {
                            writeFile file:nomeEstagio + '.2.txt', text:nomeEstagio + '.2', encoding:'UTF-8'
                        }, (nomeEstagio + '.3'): {
                            writeFile file:nomeEstagio + '.3.txt', text:nomeEstagio + '.3', encoding:'UTF-8'
                        }
                    )
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
