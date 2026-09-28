import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ListaTarefas {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<String> lista = new ArrayList<>();

        while(true){
            System.out.println("Selecione um dos itens:");
            System.out.println("1- Adicionar tarefa");
            System.out.println("2- Listar tarefas");
            System.out.println("3- Concluir tarefa");
            System.out.println("4- Remover tarefa");
            System.out.println("5- Encerrar sistema e salvar lista");
            int menu = 0;
            try{
                menu = sc.nextInt();
                sc.nextLine();
                if(1 > menu || menu > 4){
                    System.out.println("Digite um valor entre 1 e 4");
                    continue;
                }
            }catch (InputMismatchException e){
                System.out.println("Digite um valor valido");
                sc.nextLine();
                continue;
            }
            switch (menu){
                case 1:
                    adicionarTarefa(sc,lista);
                    continue;
                case 2:
                    listarTarefas(lista);
                    continue;
                case 3:
                    concluirTarefa(lista,sc);
                    continue;
                case 4:
                    removerTarefa(sc,lista);
                    continue;
                case 5:
                    return;
            }

        }
    }
    public static void adicionarTarefa(Scanner sc, List<String> lista){
                System.out.println("Descreva a tarefa que deseja armazenar: ");
                String tarefa = sc.nextLine();
                lista.add(tarefa);

    }
    public static void listarTarefas(List<String> lista){
        if(lista.isEmpty()){
            System.out.println("Nenhuma tarefa foi encontrada");
            return;
        }
        for (int i = 0; i < lista.size(); i++) {
         System.out.printf("Tarefa Nª %d: %s\n",i+1 ,lista.get(i));
        }
    }
    public static void removerTarefa(Scanner sc, List<String> lista){
        int remove;
        try{
           System.out.println("Qual tarefa você gostaria de remover? ");
           listarTarefas(lista);
           remove = sc.nextInt()-1;
           sc.nextLine();
           lista.remove(remove);
           System.out.println("Tarefa removida com sucesso! ");
       }catch (InputMismatchException e){
            System.out.println("Digite um valor valido");
        }catch (IndexOutOfBoundsException e){
            System.out.println("Digite um valor maior ou menor que a quantidae de tarefas existentes");
        }

    }
    public static void concluirTarefa(List<String> lista,Scanner sc){
        if(lista.isEmpty()){
            System.out.println("Nenhuma tarefa foi encontrada");
            return;
        }
        while(true){
            try {
                System.out.println("Qual tarefa você gostaria de concluir?");
                listarTarefas(lista);
                int concluir = sc.nextInt();
                sc.nextLine();

                int indice = concluir-1;

                if (indice < 0 || indice >= lista.size()) {
                    System.out.println("Opção inválida! Digite um número entre 1 e " + lista.size());
                    continue;
                }

                if (lista.get(indice).contains("|CONCLUIDA|")) {
                    System.out.println("A tarefa ja está concluida");
                } else {
                    lista.set(indice, lista.get(indice) + "|CONCLUIDA|");
                    System.out.println("Tarefa concluida!");
                }
                break;
            }catch (InputMismatchException e){
                System.out.println("Você deve digitar um valor correto.");
                sc.nextLine();
            }
        }
    }
    public static void salvarArquivo(List<String> lista){

        Path arquivo = Paths.get("Lista de tarefas.txt");

        try{
            Files.write(arquivo,lista);
            System.out.println("Arquivo alterado com sucesso!");
        }catch (IOException e){
            System.out.println("Erro ao alterar arquivo");
        }
    }
    public static void lerArquivo(List<String> lista){
        Path arquivo = Paths.get("Lista de tarefas.txt");
        if(!Files.exists(arquivo)){
            System.out.println("Arquivo não encontrado");
        }
        try{
            List<String> listaCarregada = new ArrayList<>(Files.readAllLines(arquivo));
            listarTarefas(listaCarregada);
        }catch (IOException e){
            System.out.println("Erro ao ler arquivo" + e.getMessage());
        }
    }
}
