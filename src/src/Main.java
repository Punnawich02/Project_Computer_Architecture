import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        Assembler comb = new Assembler("src/program/Combination/Combination.txt","src/program/Combination/MachineCode.txt","src/program/Combination/result.txt");
        comb.ConvertAndSim();

//        Assembler div = new Assembler("src/program/Division/Division.txt","src/program/Division/MachineCode.txt","src/program/Division/result.txt");
//        div.ConvertAndSim();

//        Assembler mul = new Assembler("src/program/multiplication/multiplication.txt","src/program/multiplication/MachineCode.txt","src/program/multiplication/result.txt");
//        mul.ConvertAndSim();

//        Assembler fibo = new Assembler("src/program/Fibonacci/Fibonacci.txt","src/program/Fibonacci/MachineCode.txt","src/program/Fibonacci/result.txt");
//        fibo.ConvertAndSim();

//        Assembler fac = new Assembler("src/program/Factorial/Factorial.txt","src/program/Factorial/MachineCode.txt","src/program/Factorial/result.txt");
//        fac.ConvertAndSim();

    }
}
