import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Simulator {
    private final String filename;
    private final String result;

    public Simulator(String filename, String result) throws IOException {
        this.filename = filename;
        this.result = result;
    }

    public void simulate(){
        try {
            State state = new State();
            int[] mem = state.getInstructionMem();

            File instruction = new File(filename);
            Scanner Read_instruction = new Scanner(instruction);

            int i = 0;
            while (Read_instruction.hasNextLine()) {
                String data = Read_instruction.nextLine();
                int data2 = Integer.parseInt(data);
                mem[i] = data2;
                i++;
            }
            Read_instruction.close();
            state.setInstructionMem(mem);

            Decoder d = new Decoder(state,result,i);
            d.simulate();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
