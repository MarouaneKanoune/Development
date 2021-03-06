package prog2_a4;

import de.hsa.games.fatsquirrel.util.ui.console.CommandTypeInfo;

public enum  MyFavoriteCommandType implements CommandTypeInfo {
    HELP("help", "  * list all commands"),
    EXIT("exit", "  * exit program"),
    ADDI("addi", "<param1>  <param2>   * simple integer add ",int.class, int.class ),
    ADDF("addf", "<param1>  <param2>   * simple float add ",float.class, float.class ),
    ECHO("echo", "<param1>  <param2>   * echos param1 string param2 times ",String.class, int.class );
    
    private String commandName;
    private String helpText;
    private Class<?>[] paramTypes;
    
    private MyFavoriteCommandType(String name,String helpText, Class... params){
        this.commandName = name;
        this.helpText = helpText;
        paramTypes = params;
    }
    

    @Override
    public String getName() {
        return commandName;
    }

    @Override
    public String getHelpText() {
        return helpText;
    }

    @Override
    public Class<?>[] getParamTypes() {
        return paramTypes;
    }
    
    public void help(){
        for(int i=0;MyFavoriteCommandType.values().length>i;i++){
            System.out.println(MyFavoriteCommandType.values()[i].getName()+MyFavoriteCommandType.values()[i].getHelpText());
        }
    }
    
    public void addi(int int1, int int2){
        int sum=0;
        sum = int1+int2;
        System.out.println("Ihre Zahl lautet: "+(sum));
    }
    
    public void addf(float float1, float float2){
        System.out.println("Ihre Zahl lautet: "+(float1+float2));
    }
    
    public void echo(String string, int times){
        while(times-->0)
            System.out.println(string);
    }

    public void exit(){
        System.exit(0);
    }

}
