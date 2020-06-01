package SequentialRunner;

import Args.ArgsManager;
import program.mainProgram;
import main.SmallProgram;

public class main{

    public static void main(String args[]){
        for(int i = 0; i < args.length; ++i){
            System.out.println(args[i]);
        }
        System.out.println("I am doing something!1");
        if(args.length != 10){
            System.out.println("Not enough arguments. 10 are expected and " + args.length + " are given.");
            new ArgsManager().printArgsInstructions();
            new mainProgram().printArgsInstructions();
            return;
        }
        System.out.println("I am doing something!2");
        String[] args1 = {args[0], args[1], args[2], args[3]};
        new SmallProgram().secondMainFunction(args1);
        String[] args2 = {args[4], args[5], args[6], args[7], args[8], args[9]};
        new mainProgram().anAdditionMainFunction(args2);
        System.out.println("I am doing something!3");
    }
}