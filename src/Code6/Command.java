package Code6;

import Client.Work;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicReference;

public class Command implements Serializable{
    private static final long serialVersionUID = 15L;

    public static class Show implements Workable, Serializable{
        private static final long serialVersionUID = 1L;
        private static Command.Show show = new Command.Show();
        CollectionOrganization collection;
        private Show(){
            this.collection = CollectionOrganization.getCollectionOrganization();
        }
        public static Command.Show getShow(){
            return show;
        }
        @Override
        public String work() {
            String str = "";
            for (Organization org : collection.getCollection()) {
                str += org + "\n";
            }
            return str;
        }
    }



    public static class Add implements Workable, Serializable{
        private static final long serialVersionUID = 2L;
        private static Command.Add add = new Command.Add();
        private Command.Add addServer;
        private CollectionOrganization collection;
        private Organization org;
        private Add(){
            this.collection = CollectionOrganization.getCollectionOrganization();
        }
        public static Command.Add getAdd(){
            return add;
        }
        public void setOrganization(Organization org){
            this.org = org;
            addServer = add;
        }
        @Override
        public String work() {
            if (addServer.org != null) {
                collection.getCollection().add(addServer.org);
                return "Organization was added";
            }
            else
                return "Organization wasn't added";
        }
    }


    public static class UpdateElement implements Workable, Serializable {
        private static final long serialVersionUID = 3L;
        private static Command.UpdateElement updateElement = new Command.UpdateElement();
        private CollectionOrganization collection;
        private Organization org;
        private long id;
        private UpdateElement(){
            this.collection = CollectionOrganization.getCollectionOrganization();
        }
        public static Command.UpdateElement getUpdateElement(){
            return updateElement;
        }
        public void setId(long id){
            this.id = id;
        }

        public void setOrg(Organization org) {
            this.org = org;
        }

        @Override
        public String work() {
            Command.Remove remove = Command.Remove.getRemove();
            remove.setId(id);
            remove.work();
            org.setId(id);
            collection.getCollection().add(org);
            return "Organization was updated";
        }
    }



    public static class Remove implements Workable, Serializable {
        private static final long serialVersionUID = 4L;
        private static Command.Remove remove = new Command.Remove();
        private Command.Remove removeServer;
        private CollectionOrganization collection;
        private long id;

        private Remove() {
            this.collection = CollectionOrganization.getCollectionOrganization();
        }

        public static Command.Remove getRemove() {
            return remove;
        }

        public Command.Remove getRemoverServer() {
            return removeServer;
        }

        public void setId(long id) {
            this.id = id;
            removeServer = remove;
        }

        public long getIdRemoverServer() {
            return removeServer.id;
        }

        @Override
        public String work() {
            AtomicReference<Organization> org = new AtomicReference<>();
            removeServer.collection.getCollection().stream().
                    filter(o -> o.getId() == removeServer.id).limit(1).
                    forEach(o -> org.set(o));
            removeServer.collection.getCollection().remove(org.get());
            return "Organization with id " + removeServer.id + " was removed";
        }
    }



    public static class Clear implements Workable, Serializable {
        private static final long serialVersionUID = 5L;
        private static Command.Clear clear = new Command.Clear();
        private CollectionOrganization collection;

        private Clear() {
            this.collection = CollectionOrganization.getCollectionOrganization();
        }

        public static Command.Clear getClear() {
            return clear;
        }

        @Override
        public String work()
        {
            collection.getCollection().clear();
            return "Collection was cleared";
        }
    }



    public static class ExecuteScript implements Workable, Serializable {
        private static final long serialVersionUID = 6L;
        private static Command.ExecuteScript executeScript = new Command.ExecuteScript();
        private CollectionOrganization collection;
        private String path;
        private ArrayList<Workable> listWork = new ArrayList<>();
        private ExecuteScript() {
            this.collection = CollectionOrganization.getCollectionOrganization();
        }

        public static Command.ExecuteScript getExecuteScript() {
            return executeScript;
        }

        public void setFilePath(String path) {
            this.path = path;
        }

        public ArrayList<Workable> getListWork() {
            return listWork;
        }

        @Override
        public String work() { //execute_script C:/Users/Daniil/Desktop/Универ/Прога/lab5/File/start.json

            try (BufferedReader reader = new BufferedReader(new FileReader(path))){
                Work.goScriptWork(reader);
            } catch (IOException e) {
                System.out.println("Введен некорректный адрес");
            } catch (NullPointerException e) {
                System.out.println("Передано пустое значение");
            } catch (StackOverflowError error) {
                System.out.println("ну... тут мои полномочия все, память закончилась" +
                        "\n" + "введите нову команду");

            }
            return "Script finished work";
        }
    }


    public static class AddIfMin implements Workable, Serializable {
        private static final long serialVersionUID = 7L;
        private static Command.AddIfMin addIfMin = new Command.AddIfMin();
        private Command.AddIfMin addIfMinServer;
        private CollectionOrganization collection;
        private Organization org;

        private AddIfMin() {
            this.collection = CollectionOrganization.getCollectionOrganization();
        }

        public static Command.AddIfMin getAddIfMin() {
            return addIfMin;
        }

        public void setOrganization(Organization org) {
            this.org = org;
            addIfMinServer = addIfMin;
        }

        @Override
        public String work() {
            if (addIfMinServer.org.hashCode() < collection.getCollection().first().hashCode() && addIfMinServer.org != null) {
                collection.getCollection().add(addIfMinServer.org);
                return "Organization was added";
            }
            else return  "Organization wasn't added";
        }
    }

    public static class RemoveGreater implements Workable, Serializable{
        private static final long serialVersionUID = 8L;
        private static Command.RemoveGreater removeGreater = new Command.RemoveGreater();
        private CollectionOrganization collection;
        private String element;

        private RemoveGreater() {
            this.collection = CollectionOrganization.getCollectionOrganization();
        }

        public static Command.RemoveGreater getRemoveGreater() {
            return removeGreater;
        }

        public void setFileName(String element) {
            this.element = element;
        }

        @Override
        public String work() {
            Organization organization = null;
            for (Organization o : collection.getCollection()) {
                if (o.getName().equals(element)) {
                    organization = o;
                    break;
                }
            }
            if (organization == null) {
                return "Organizations \"greater\" specified weren't deleted\nincorrect name element";
            } else {
                while (true) {
                    Organization o = collection.getCollection().higher(organization);
                    if (o != null)
                        collection.getCollection().remove(o);
                    else
                        break;
                }
            }
            return "Organizations \"greater\" specified were deleted";
        }
    }

    public static class RemoveLower implements Workable, Serializable {
        private static final long serialVersionUID = 9L;
        private static Command.RemoveLower removeLower = new Command.RemoveLower();
        private CollectionOrganization collection;
        private String element;

        private RemoveLower() {
            this.collection = CollectionOrganization.getCollectionOrganization();
        }

        public static Command.RemoveLower getRemoveLower() {
            return removeLower;
        }

        public void setFileName(String element) {
            this.element = element;
        }

        @Override
        public String work() {
            Organization organization = null;
            for (Organization o : collection.getCollection()) {
                if (o.getName().equals(element)) {
                    organization = o;
                    break;
                }
            }
            if (organization == null) {
                return "Organizations \"greater\" specified weren't deleted\nincorrect name element";
            } else {
                while (true) {
                    Organization o = collection.getCollection().lower(organization);
                    if (o != null)
                        collection.getCollection().remove(o);
                    else
                        break;
                }
            }
            return "Organizations \"lower\" specified were removed";
        }
    }


    public static class CountGreaterThanType implements Workable, Serializable {
        private static final long serialVersionUID = 10L;
        private static Command.CountGreaterThanType countGreaterThanType = new Command.CountGreaterThanType();
        private Command.CountGreaterThanType countGreaterThanTypeServer;
        private CollectionOrganization collection;
        private String typeS;

        private CountGreaterThanType() {
            this.collection = CollectionOrganization.getCollectionOrganization();
        }

        public static Command.CountGreaterThanType getCountGreaterThanType() {
            return countGreaterThanType;
        }

        public void setType(String typeS) {
            this.typeS = typeS;
            countGreaterThanTypeServer = countGreaterThanType;
        }

        @Override
        public String work() {
            long count = 0;
            OrganizationType type = null;
            if (countGreaterThanTypeServer.typeS.equalsIgnoreCase(OrganizationType.COMMERCIAL.toString())) {
                type = OrganizationType.COMMERCIAL;
            } else if (countGreaterThanTypeServer.typeS.equalsIgnoreCase(OrganizationType.PUBLIC.toString())) {
                type = OrganizationType.PUBLIC;
            } else if (countGreaterThanTypeServer.typeS.equalsIgnoreCase(OrganizationType.TRUST.toString())) {
                type = OrganizationType.TRUST;
            } else {
                return "You entered an incorrect value";
            }

            OrganizationType finalType = type;
            count = collection.getCollection().stream().filter(o -> o.getType().VALUE > finalType.VALUE).count();

            if (count >= 0)
               return "Number organization with id greater " + countGreaterThanTypeServer.typeS + ": " + count;
            else
                return "Not found organization with id greater " + countGreaterThanTypeServer.typeS;
        }
    }

    public static class PrintDescending implements Workable, Serializable {
        private static final long serialVersionUID = 11L;
        private static Command.PrintDescending printDescending = new Command.PrintDescending();
        private CollectionOrganization collection;

        private PrintDescending() {
            this.collection = CollectionOrganization.getCollectionOrganization();
        }

        public static Command.PrintDescending getPrintDescending() {
            return printDescending;
        }

        @Override
        public String work() {
            String str = "";
            TreeSet<Organization> deOr = (TreeSet<Organization>) collection.getCollection().descendingSet();
            for (Organization o : deOr) {
                str += o.getName()+"\n";
            }
            return str;
        }
    }


    public static class PrintFieldAscendingOfficialAddress implements Workable, Serializable {
        private static final long serialVersionUID = 12L;
        private static Command.PrintFieldAscendingOfficialAddress printFieldAscendingOfficialAddress =
                new Command.PrintFieldAscendingOfficialAddress();
        private CollectionOrganization collection;

        private PrintFieldAscendingOfficialAddress() {
            this.collection = CollectionOrganization.getCollectionOrganization();
        }

        public static Command.PrintFieldAscendingOfficialAddress getPrintFieldAscendingOfficialAddress() {
            return printFieldAscendingOfficialAddress;
        }

        @Override
        public String work() {
            TreeSet<Address> treeAddress = new TreeSet<Address>(new Comparator<Address>() {
                @Override
                public int compare(Address o1, Address o2) {
                    return o1.getZipCode().compareTo(o2.toString());
                }
            });
            String str = "";
            collection.getCollection().stream().forEach(o -> treeAddress.add(o.getOfficialAddress()));
            for (Address s : treeAddress)
                str +=s+"\n";
            return str;
        }
    }

    public static class Help implements Workable, Serializable {
        private static final long serialVersionUID = 13L;
        private static Command.Help help = new Command.Help();
        private Help(){
        }

        public static Command.Help getHelp(){
            return help;
        }

        @Override
        public String work() {
            return "help : вывести справку по доступным командам\n" +
                    "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                    "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                    "add: добавить новый элемент в коллекцию\n" +
                    "update_id {element} : обновить значение элемента коллекции, id которого равен заданному\n" +
                    "remove_by_id id : удалить элемент из коллекции по его id\n" +
                    "clear : очистить коллекцию\n" +
                    "execute_script {file_name} : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                    "exit : завершить программу (без сохранения в файл)\n" +
                    "add_if_min {element} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции\n" +
                    "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный\n" +
                    "remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный\n" +
                    "count_greater_than_type type : вывести количество элементов, значение поля type которых больше заданного\n" +
                    "print_descending : вывести элементы коллекции в порядке убывания\n" +
                    "print_field_ascending_official_address: вывести значения поля officialAddress в порядке возрастания";
        }
    }

    public static class Exit implements Workable, Serializable {
        private static final long serialVersionUID = 14L;
        private static Command.Exit exit = new Command.Exit();

        private Exit() {
        }

        public static Command.Exit getExit() {
            return exit;
        }

        @Override
        public String work() {
            System.exit(4221);
            return "этого мы не увидим никогда))))";
        }
    }

    public static class Info implements Workable, Serializable {
        private static Command.Info info = new Command.Info();
        private CollectionOrganization collection;

        private Info(){
            this.collection = CollectionOrganization.getCollectionOrganization();
        }

        public  static Command.Info getInfo(){
            return info;
        }

        @Override
        public String work() {
            String text = "";
            text += "Collection: " + collection.getCollection().getClass().getSimpleName() + "\n";
            text += "Date of initialization: " + collection.getDate().toString() + "\n";
            text += "Number elements: " + collection.getCollection().size() + "\n";
            if(collection.getCollection().size() != 0){
                text += "The last collection element: " + collection.getCollection().last().toString() + "\n";
                text += "The first collection element: " + collection.getCollection().first().toString() + "\n";
            }
            return text;
        }
    }

    public static class Save implements Workable, Serializable{
        private static Command.Save save = new Command.Save();
        private static String path = "result.json";
        private Collection collection;
        private Save(){
        }

        public static Command.Save getSave(){
            return save;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public void setCollection(Collection collection) {
            this.collection = collection;
        }

        @Override
        public String work() {
            Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            collection = new Collection(CollectionOrganization.getCollectionOrganization().getCollection());
            String result = GSON.toJson(collection);
            try{
                PrintWriter printWriter = new PrintWriter(new FileOutputStream(path));
                printWriter.write(result);
                printWriter.close();
            } catch (IOException e) {
                System.out.println("*happens* IOException");
            }
            return "Collection was saved";
        }
    }
}
