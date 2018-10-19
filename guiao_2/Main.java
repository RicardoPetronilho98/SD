/**
 * Escreva um programa que crie N threads, em cada uma escreva os números de 1 a I, e depois espere que estas terminem.
 */
public class Main {

    /*
    EXPLICAÇÃO da necessidade de sincronização entre Threads:

    NOTA: ------------------------------------------------------------------------------------------------
    A operação ----> c.v++ <---- é equivalente a:

    R (registo) <- c.v
    R++
    c.v <- R

    ou seja, apesar de parecer, não é uma operação atómica
    ------------------------------------------------------------------------------------------------------

    Supõe-se que existe um obejto, Counter c:

    temos um estado inicial em que: c.v = 0

     t0
   ---|---
      |
      |
      |
      |           t1
      | ----------|---
      |           |
      |           |
      |           |                t2
      | ---------------------------|---
      |           |                |
      |           |                |
      |         R <- c.v (0)       |
      |   inc() R++      (1)       |
      |         c.v <- R           |
      |           |                |
      |           |                |
      |          R <- c.v (1)      |
      |       (t1 é adormecida)    |
      |           |                |
      |           |              R <- c.v (1)  |
      |           |        inc() R++      (2)  |  x1000 (no fim c.v = 10002)
      |           |              c.v <- R      |
      |           |                |
      |           |                |
      |       (t1 é retomada)      |
      |          R++      (2)      |
      |          c.v <- R          |


      nota: com isto perdeu-se o trabalho de 1000 iterações

      synchronized -> it is not possible for two invocations of synchronized methods
      on the same object to interleave. When one thread is executing a synchronized method for an object,
      all other threads that invoke synchronized methods for the same object block (suspend execution)
      until the first thread is done with the object.
      */

    public static void main (String[] args) {
        // exe1();
        // exe2();
        exe3();
    }

    private static final int N_THREADS = 100;

    /**
     * Opção de criar a Thread1 ao extender a class Thread.
     */
    public static void exe1() {

        Counter c = new Counter();
        long beforeTime = System.currentTimeMillis();

        Thread[] ths = new Thread[N_THREADS];

        for(int i = 0; i < ths.length; i++) {
            ths[i] = new Thread1(c); // criar a Thread
            ths[i].start(); // iniciar o trabalho da Thread
        }

        for(int i = 0; i < ths.length; i++) {
            try {
                ths[i].join(); // esperar que cada Thread acabe
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("c=" + c.c);
        System.out.println("t=" + (System.currentTimeMillis() - beforeTime));
    }

    private static final int N_CONTAS = 1000;
    private static final int N_AGENCIAS = 100;

    /**
     * Cria 100 Agencias que realiza 1000 operações sobre as 1000 contas de 1 Banco.
     * Neste caso a sincronização é ao nível do objeto Banco.
     */
    public static void exe2() {

        int saldoAgencias = 0;

        Banco banco = new Banco(N_CONTAS);

        Agencia[] agencias = new Agencia[N_AGENCIAS]; // inicializa o array de Agencias

        for (int i = 0; i < agencias.length; i++) {
            agencias[i] = new Agencia(banco); // cria cada Agencia
            agencias[i].start(); // iniciar o trabalho (operações) de cada Agencia
        }

        for (int i = 0; i < agencias.length; i++) {
            try {
                agencias[i].join(); // esperar que a Agencia acabe as operações
                saldoAgencias += agencias[i].getSaldo(); // soma o saldo interno de cada Agencia
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /*
        NOTA: para que exista uma PROVA que realmente não houverem operações mal feitas (Threads MAL sincronizadas)
              soma-se o saldo interno de todas as Agencias, e esse saldo tem de ser igual ao saldo interno do Banco.
              Ou seja, o próximo System.println() devia indicar -> true <-
         */
        System.out.println( (banco.getSaldo() == saldoAgencias) + " | saldo total = " + banco.getSaldo());
    }

    /**
     * Cria 100 Agencias que realiza 1000 operações sobre as 1000 contas de 1 Banco.
     * Neste caso a sincronização é ao nível do objeto Conta.
     */
    public static void exe3() {

        int saldoAgencias = 0;

        Banco2 banco = new Banco2(N_CONTAS);

        Agencia2[] agencias = new Agencia2[N_AGENCIAS]; // inicializa o array de Agencias

        for (int i = 0; i < agencias.length; i++) {
            agencias[i] = new Agencia2(banco); // cria cada Agencia
            agencias[i].start(); // iniciar o trabalho (operações) de cada Agencia
        }

        for (int i = 0; i < agencias.length; i++) {
            try {
                agencias[i].join(); // esperar que a Agencia acabe as operações
                saldoAgencias += agencias[i].getSaldo(); // soma o saldo interno de cada Agencia
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /*
        NOTA: para que exista uma PROVA que realmente não houverem operações mal feitas (Threads MAL sincronizadas)
              soma-se o saldo interno de todas as Agencias, e esse saldo tem de ser igual ao saldo interno do Banco.
              Ou seja, o próximo System.println() devia indicar -> true <-
         */
        System.out.println(banco.balanco() == saldoAgencias);
    }
}