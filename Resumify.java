import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Q4 {
    static int flagForReg=0;
    static int flagForData=0;
    public static void main(String[] args) {

        Pattern patternForBio = Pattern.compile("\\s*bio\\s*\"(?<biography>.*)\\s*\"");
        Scanner scanner = new Scanner(System.in);
        String inputLine;
        String[] informarion = new String[5];
        while (true) {
            inputLine=scanner.nextLine().trim();
            Matcher matcherForBio = patternForBio.matcher(inputLine);
            int flagForInvalid=0;
        
            if(matcherForBio.matches() && flagForReg==1 && flagForData==1){
                String biography = matcherForBio.group("biography");
                String lastBio =formatDescription(biography);
                informarion[4]=lastBio;
                printingResult(informarion);
                break;
            }
            if (inputLine.startsWith("register") && flagForReg==0) {
               
                flagForInvalid=1;
                register(inputLine);
               
            } if (flagForReg==1 && inputLine.startsWith("data") && flagForData==0) {
                flagForInvalid=1;    
                
                    Data(informarion,inputLine);
                }
        else if(flagForInvalid==0) {
            System.out.println("invalid command");
           
        }
    }
}

static boolean isDataFlagTrue(String data){
    for(int a=0;a<data.length();a++){
        if(data.charAt(a)== ' '){
            if(data.charAt(a+1)== '-'){
                
                if(!(data.charAt(a+2)== 'e' || (data.charAt(a+2)== 'l' && data.charAt(a+3)=='n') || 
                (data.charAt(a+2)== 'f' && data.charAt(a+3)=='n') || (data.charAt(a+2)== 'p' && data.charAt(a+3)=='h')) ){
                    return false;
                }
            }
        }
    }
    return true;
}

static int Data(String [] information,String inputLine){
    Pattern patternForFirstCheckData = Pattern.compile("\\s*data\\s+(?=.*-fn\\s+(?<firstname>\\S+)?\\s*)(?=.*-e\\s+(?<email>\\S+)?\\s*)(?=.*-ln\\s+(?<lastname>\\S+)?\\s*)(?=.*-ph\\s+(?<phonenumber>\\S+)?\\s*)(\\s*.*-[(fn)|(e)|(ln)|(ph)]\\s*\\S+.*){4}");  
    Matcher matcherForFirstTime = patternForFirstCheckData.matcher(inputLine);
    if(matcherForFirstTime.matches()){
        
        String firstName = matcherForFirstTime.group("firstname");
        String lastName = matcherForFirstTime.group("lastname");
        String email = matcherForFirstTime.group("email");
        String phoneNumber = matcherForFirstTime.group("phonenumber");
        if(checkForFirstName(firstName)){
           
            if(checkForLastName(lastName)){
                if(!isDataFlagTrue(inputLine)){
                    System.out.println("wrong flag format");
                    return 0;
                }
                if(checkForEmail(email)){
                    if(checkForPhoneNumber(phoneNumber)){
                        flagForData=1; 
                        System.out.println("data saved successfully");
                        information[0] = firstName;
                        information[1] = lastName;
                        information [2] = email;
                        information [3] = phoneNumber;
                        return 1;
                    }
                    else{
                        flagForData=0;
                        System.out.println("invalid phone number");
                        return 0;
                    }
                }
                else{
                    flagForData=0;
                    System.out.println("invalid email format");
                    return 0;
                }
            }
            else{
                flagForData=0;
                System.out.println("wrong name format");
                return 0;
            }
        }
        else{
            flagForData=0;
            System.out.println("wrong name format");
            return 0;
        }
    }
    else{
        flagForData=0;
        System.out.println("wrong flag format");
        return 0;
    }
}


static boolean checkForPhoneNumber(String phoneNumber){
    Pattern checkForPhoneNumber = Pattern.compile("(\\+989|09)[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]");
    Matcher matchForPhoneNumber = checkForPhoneNumber.matcher(phoneNumber);
    if(matchForPhoneNumber.matches()){
        return true;
    }
    return false;
}

static boolean checkForEmail(String email){
    Pattern firstCheckForemail = Pattern.compile("^(?<username>.*)@([a-zA-Z]+[.]?[a-zA-Z]+)?\\.com");
    Matcher matchforFirstForEmail = firstCheckForemail.matcher(email);
    if(matchforFirstForEmail.matches()){
        String userName = matchforFirstForEmail.group("username");
        Pattern patternForCheckUserName = Pattern.compile("^(?!.*\\\\.{2,})(?!.*_{2,})(?!.*[._]{2,})(?!.*[._]$)(?!.*\\\\.$)[a-zA-Z][a-zA-Z0-9._]*");
        Matcher matchForUserName = patternForCheckUserName.matcher(userName);
        if(matchForUserName.matches()){
            return true;
        }
        else{
            return false;
        }
    }
    else{
        return false;
    }
}

static boolean checkForLastName(String lastName){
    Pattern chaeckForlastName = Pattern.compile("([a-zA-Z]+[-][a-zA-Z]+)|[a-zA-Z]*");
    Matcher matchForlastName = chaeckForlastName.matcher(lastName);
    if(matchForlastName.matches()){
        return true;
    }
   
        return false;
}

static boolean checkForFirstName(String firstName){
    Pattern checkForFirstName = Pattern.compile("[a-zA-Z]*");

    Matcher matchForFirstName = checkForFirstName.matcher(firstName);
    if(matchForFirstName.matches()){
        return true;
    }
   
        return false;
    
}

static int register(String inputLine){
    Pattern patternForFirstCheckRegister = Pattern.compile("\\s*register\\s*(?=.*-u\\s+(?<username>\\S+)+\\s*)(?=.*-p\\s+(?<password>\\S+)+\\s*)(\\s*-[up]\\s+\\S+){2}");  
    Matcher matcherForFirstTime = patternForFirstCheckRegister.matcher(inputLine);
    int flagForPass=1;

    if(matcherForFirstTime.matches()){
      
        String username = matcherForFirstTime.group("username");
        String password = matcherForFirstTime.group("password");
        Pattern patternForCheckUserName = Pattern.compile("^(?!.*\\.{2,})(?!.*_{2,})(?!.*[._]{2,})(?!.*[._]$)(?!.*\\.$)[a-zA-Z][a-zA-Z0-9._]*");
        Matcher matchForUserName = patternForCheckUserName.matcher(username);
        Pattern patternForPass = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()-+=])(?!.*(.)\\1{3,})[A-Za-z\\d!@#$%^&*()-+=]{6,20}$");
        Matcher matchForPass = patternForPass.matcher(password);
        if(matchForUserName.matches()){
            if(matchForPass.matches()){

             String[] splitted = username.split("[1234567890_\\.]");
                for (String split: splitted) {

                    if(split.length()>=4){

                        if(!(isInUserName(split,password))){
                            System.out.println("weak password");
                          
                           flagForPass=0;
                           flagForReg=0;
                           break;
                        }
                        
                    }
                }
            }
            else {
                flagForReg=0;
                System.out.println("weak password");

  flagForPass=0;
return 0;
            }
            if(flagForPass==1){
                System.out.println("register successful");
                flagForReg=1;
                return 1;
            }
           
        }
        else{
           flagForReg=0;
            System.out.println("invalid username format");
return 0;
        }
    }
    else{
        flagForReg=0;
        System.out.println("wrong flag format");
return 0;
    }
return 1;
}

static boolean isInUserName(String splitted,String password){
    if(password.contains(splitted)){

        return false;
    }
    return true;
}

static void printingResult(String[] information){
 String firstName = capitalizeFirstLetter(information[0]);
 String lastName = capitalizeFirstCharForLastName(information[1]);
 String email = information[2].toLowerCase();
 String phoneNumber = changePhoneNumberToStanderd(information[3]);
 String biography = information[4];
 System.out.println("----------");
 System.out.println(firstName+" "+lastName);
 System.out.println("Email: "+email);
 System.out.println("Phone Number: "+phoneNumber);
 System.out.println();
 System.out.println("Biography:");

 System.out.println(biography);
 System.out.println("----------");
}

static String formatDescription(String description) {
        // Remove leading and trailing white spaces
        description = description.trim();

        // Replace multiple spaces with a single space
        description = description.replaceAll(" +", " ");

        // Replace more than two consecutive newlines with just two newlines
       description = description.replaceAll("\\\\n", "\n");
       description = description.replaceAll("\n{3,}","\n\n");

        // Format description to have maximum 40 characters per line
        StringBuilder formattedDescription = new StringBuilder();
        int lineLength = 0;

        String[] lines = description.split("\n");

        for (String line : lines) {

        if(line !=""){

            String[] words = line.split(" ");
            for (String word : words) {if(word!="") {
                if (lineLength + word.length() <= 40) {
                     if(lineLength + word.length() + 1>40){


                        formattedDescription.append(word);
                        formattedDescription.append("\n").append(" ");
                        lineLength = 1;
                        continue;
                }
                    formattedDescription.append(word).append(" ");
                    lineLength += word.length() + 1;
                } else {
                    formattedDescription.append("\n").append(word).append(" ");
                    lineLength = word.length() + 1;
                }
        }
    }
}
            formattedDescription.append("\n");
            lineLength = 0;
        }
        
        return formattedDescription.toString().trim();
    }

    static String changePhoneNumberToStanderd(String phoneNumber){
    StringBuilder result = new StringBuilder();
    int index=0;
    if(phoneNumber.startsWith("0")){
        result.append("+98-");
        for (char c : phoneNumber.toCharArray()) {
            if(index==3 || index==6){
                result.append(c);
                result.append("-");
            }
            else if(index>=1){
                result.append(c);
            }
            index++;

        }
        
    }
    else if(phoneNumber.startsWith("+")){
        for (char c : phoneNumber.toCharArray()) {
            if(index ==2 || index ==5 || index == 8){
                result.append(c);
                result.append("-");
            }
            else{
                result.append(c);
            }
            index++;
        }
        
    }
return result.toString();
}

static String capitalizeFirstCharForLastName(String input) {
     
    StringBuilder result = new StringBuilder();
    boolean capitalizeNext = true;
    
    for (char c : input.toCharArray()) {
        if (Character.isLetter(c)) {
            if (capitalizeNext) {
                result.append(Character.toUpperCase(c));
                capitalizeNext = false;
            } else {
                result.append(Character.toLowerCase(c));
            }
        } else if (c == '-') {
            result.append(c);
            capitalizeNext = true;
        } else {
            
            result.append(c);
        }
    }
    
    return result.toString();
}

static String capitalizeFirstLetter(String input) {
    StringBuilder result = new StringBuilder();
    String firstLetter = input.substring(0, 1).toUpperCase();
    String restOfString = input.substring(1).toLowerCase();
    result.append(firstLetter);
    result.append(restOfString);
    return result.toString();
}
}