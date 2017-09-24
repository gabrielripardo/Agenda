echo ''
echo "                                Agenda_Console"
echo ''
echo ''
echo "                                  versão 1.3"
echo "                              por Gabriel Ripardo"
echo " A Agenda_Console é um simples open-source feito em java para fins de aprendizado"
echo ''

#[1] Entrando no diretório
cd src

#[2] Compilando a classe com o método principal
javac Start.java

#[3] Rodando o programa junto com driver do SQLite
java -cp sqlite-jdbc-3.20.0.jar: Start


