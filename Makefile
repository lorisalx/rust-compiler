parser:
	java -jar ./lib/antlr-4.9.2-complete.jar rust.g4 -no-listener -visitor -o ./src/parser

compile:
	javac -cp ./lib/antlr-4.9.2-complete.jar:./src ./src/Main.java -d ./bin

setup: parser compile

st:
	java -cp ./lib/antlr-4.9.2-complete.jar:./bin Main $(target) false

ast:
	@echo -e "\e[1;37mGenerate dot:\e[0m"
	java -cp ./lib/antlr-4.9.2-complete.jar:./bin Main $(target) true
	@echo -e "\n\e[1;37mConvert dot to svg:\e[0m"
	dot -Tsvg ./out/tree.dot -o ./out/tree.svg
	@echo -e "\n\e[1;32msvg available at ./out/tree.svg\e[0m"

runcode:
	@echo -e "\e[1;37mCompilation de ${target}...\e[0m"
	@rustc $(target) -o "compiled_code"
	@echo -e "\n\e[1;37mExecution de compiled_code...\e[0m"
	@./"compiled_code"
