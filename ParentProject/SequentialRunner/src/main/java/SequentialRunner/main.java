package SequentialRunner;

import Args.ArgsManager;
import program.Manager;
import main.SmallProgram;

public class main{

    public static void main(String args[]){
        for(int i = 0; i < args.length; ++i){
            System.out.println(args[i]);
        }
        System.out.println("I am doing something!1");
        if(args.length != 14){
            System.out.println("Not the right amount of arguments. 14 are expected and " + args.length + " are given.");
            new ArgsManager().printArgsInstructions();
            new Manager().printArgsInstructions();
            return;
        }
        System.out.println("I am doing something!2");
        String[] args1 = {args[0], args[1], args[2], args[3], args[4], args[5]};
        new SmallProgram().secondMainFunction(args1);
        String[] args2 = {args[6], args[7], args[8], args[9], args[10], args[11], args[12], args[13]};
        new Manager().anAdditionMainFunction(args2);
        System.out.println("I am doing something!3");
    }
}