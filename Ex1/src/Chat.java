import java.util.Scanner;

class Chat {
    public static void main(String[] args) {
        String [] lst1 = {"what","say I should say"};
        String [] lst2 ={"whaaat","say say"};
        ChatterBot[] arr = new ChatterBot[2];
        ChatterBot bot1=new ChatterBot("Michael",new String[]{"say <phrase>? okay: <phrase>"},lst1);
        ChatterBot bot2 =new ChatterBot("Sarah",new String[]{"say <phrase>? okay: <phrase>"},lst2);
        arr[0] = bot1;
        arr[1] = bot2;
        String statement="";
        for(int i=0;i< arr.length;i++){
            statement=arr[i].replyTo(statement);
            System.out.println(arr[i].getName() + ": "+statement);

        }
        System.out.println(bot1.replyTo("say door"));
        System.out.println(bot1.replyTo("say something"));

    }

}
