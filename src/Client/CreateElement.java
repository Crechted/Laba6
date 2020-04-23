package Client;

import Code6.Address;
import Code6.Coordinates;
import Code6.Organization;
import Code6.OrganizationType;

import java.io.BufferedReader;


/**
 * this class creat new element collection.
 * @see Work
 * @see Code6.CollectionOrganization
 */
public class CreateElement {
//    private String name; //Поле не может быть null, Строка не может быть пустой
//    private Coordinates coordinates; //Поле не может быть null
//    private float annualTurnover; //Значение поля должно быть больше 0
//    private Integer employeesCount; //Поле не может быть null, Значение поля должно быть больше 0
//    private OrganizationType type; //Поле может быть null
//    private Address officialAddress; //Поле может быть null

    /**
     *
     * @param reader this parameter can implement both keyboard input and reading from a file
     * @param realization this parameter is enum Work.Realization. It parameter specifies how the work is performed, either interactively or using a script from a file
     * @return return element collection with type Organization.
     */
    public static Organization createElement(BufferedReader reader, Work.Realization realization){
        String name = null;
        Long X = null;
        Integer Y = null;
        Coordinates coordinates = null;
        Float annualTurnover = null;
        Integer employeesCount = null;
        OrganizationType type = null;
        Address address = null;
        if(realization == Work.Realization.InteractiveWork){
            System.out.println("Введите имя нового елемента" +
                        "\n (имя не должно быть пустым):");
            name = getName(reader, realization);

            System.out.println("Введите координату X нового елемента" +
                    "\n (должно быть больше -904, иначе автоматически будет выставлено -903)" +
                    "\n не устанавливайте значения привышающие +-" + Long.MAX_VALUE + ". число должно быть целым):");
            X = getCoordinatesX(reader, realization);
            System.out.println("Введите координату Y нового елемента" +
                    "\n (установите значение не боьше 551, в противном случе автоматически будет выставлено 551." +
                    "\n не устанавливайте значения привышающие +-" + Integer.MAX_VALUE + ". число должно быть целым):");
            Y = getCoordinatesY(reader, realization);

            System.out.println("Введите размер годового оборота"+
                    "\n (должен быть больше 0):" +
                    "\n не устанавливайте значения привышающие +-" + Float.MAX_VALUE + ". число не обязательно должно быть целым):");
            annualTurnover = getAnnualTurnover(reader, realization);
            System.out.println("Введите колличество сотрудников"+
                    "\n (должен быть больше 0):" +
                    "\n не устанавливайте значения привышающие +-" + Integer.MAX_VALUE + ". число должно быть целым):");
            employeesCount = getEmployeesCount(reader, realization);
            System.out.println("Введите тип елемента"+
                    "\n (доступны три типа  COMMERCIAL, PUBLIC, TRUST. Регистр не учитывается):");
            type = getType(reader, realization);
            System.out.println("Введите адрес"+
                    "\n (адрес должен иметь хотя бы 1 символ, и не должен содержать больше 15):");

            address = getOfficialAddress(reader, realization);
        }else if(realization == Work.Realization.ScriptWork){
            name = getName(reader, realization);
            if(name == null)
                return null;
            X = getCoordinatesX(reader, realization);
            if(X == null)
                return null;
            Y = getCoordinatesY(reader, realization);
            if(Y == null)
                return null;
            annualTurnover = getAnnualTurnover(reader, realization);
            if(annualTurnover == null)
                return null;
            employeesCount = getEmployeesCount(reader, realization);
            if(employeesCount == null)
                return null;
            type = getType(reader, realization);
            if(type == null)
                return null;
            address = getOfficialAddress(reader, realization);
            if(name == null)
                return null;
        }
        if(name == null || annualTurnover == null || X == null || Y == null || employeesCount == null
                || type == null || address == null) {
            System.out.println("объект не будет создан, так как один из параметров оказался равен null");
            return null;
        }
//        System.out.println(reader);
//        System.out.println(reader.getClass().getSimpleName());
        coordinates = new Coordinates(X, Y);
        return new Organization(name, coordinates, annualTurnover, employeesCount, type, address);
    }

    private static String getName(BufferedReader reader, Work.Realization realization){
        //BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        if(realization == Work.Realization.InteractiveWork) {
            String name;
            while (true) {
                one:
                {
                    try {
                        name = reader.readLine();
                        if (name == null)
                            return null;

                        if (name.equals("") == false) {
                            //reader.close();
                            return name;
                        } else
                            System.out.println("имя введенно некорректно, попробуйте ввести новое значение:");


                    } catch (Exception e) {
                        System.out.println("Аыыы, исключение *происходится* . Попробуйте снова:");
                        break one;
                    }
                }
            }
        }
        else {
            String name;
            try {
                name = reader.readLine();
                if (name == null)
                    return null;

                if (name.equals("") == false) {
                    //reader.close();
                    return name;
                } else
                  return null;


            } catch (Exception e) {
                return null;
            }
        }
    }

    private static Long getCoordinatesX(BufferedReader reader, Work.Realization realization){
        //BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        if(realization == Work.Realization.InteractiveWork){
        long X;
        while (true){
            one:
            {
                try {
                    String s = reader.readLine();
                    if(s == null)
                        return null;

                    X = Long.parseLong(s);

                    if (X > -904) {
                        break;
                    } else {
                        X = -903;
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("Аыыы, исключение *происходится* . Попробуйте снова:");
                    break one;

                }
            }
        }
        return X;
        }
        else {
            long X;
            try {
                String s = reader.readLine();
                if(s == null)
                    return null;

                X = Long.parseLong(s);

                if (X <= -904) {
                    X = -903;
                }
            } catch (Exception e) {
               return null;
            }
            return X;
        }
    }

    private static Integer getCoordinatesY(BufferedReader reader, Work.Realization realization){
        if(realization == Work.Realization.InteractiveWork) {
            int Y = 0;
            while (true) {
                two:
                {
                    try {
                        String s = reader.readLine();
                        if (s == null)
                            return null;

                        Y = Integer.parseInt(s);
                        if (Y > 551) {
                            Y = 551;
                            break;
                        } else {
                            break;
                        }
                    } catch (Exception e) {
                        System.out.println("Аыыы, исключение *происходится* . Попробуйте снова:");
                        break two;
                    }
                }
            }
            return Y;
        }
        else {
            int Y;
            try {
                String s = reader.readLine();
                if (s == null)
                    return null;

                Y = Integer.parseInt(s);
                if (Y > 551) {
                    Y = 551;
                }
            } catch (Exception e) {
                return null;
            }
            return Y;
        }
    }

    private static Float getAnnualTurnover(BufferedReader reader, Work.Realization realization){
        //BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        if(realization == Work.Realization.InteractiveWork) {
            float sum = 0;
            while (true) {
                one:
                {
                    try {
                        String s = reader.readLine();
                        if (s == null)
                            return null;
                        sum = Float.parseFloat(s);
                        if (sum > 0) {
                            //reader.close();
                            return sum;
                        } else
                            System.out.println("Число меньше 0. Попробуйте ввести новое значение:");
                    } catch (Exception e) {
                        System.out.println("Аыыы, исключение *происходится* . Попробуйте снова:");
                        break one;
                    }
                }
            }
        }
        else {
            float sum;
            try {
                String s = reader.readLine();
                if (s == null)
                    return null;
                sum = Float.parseFloat(s);
                if (sum > 0) {
                    //reader.close();
                    return sum;
                } else
                   return null;
            } catch (Exception e) {
               return null;
            }
        }
    }

    private static Integer getEmployeesCount(BufferedReader reader, Work.Realization realization){
        //BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        if(realization == Work.Realization.InteractiveWork) {
            Integer sum;
            while (true) {
                one:
                {
                    try {
                        String s = reader.readLine();
                        if (s == null)
                            return null;
                        sum = Integer.parseInt(s);
                        int k = sum;
                        if (k > 0) {
                            //reader.close();
                            return sum;
                        } else
                            System.out.println("Число меньше 0. Попробуйте ввести новое значение: ");
                    } catch (Exception e) {
                        System.out.println("Аыыы, исключение *происходится* . Попробуйте снова:");
                        break one;
                    }
                }
            }
        }
        else {
            Integer sum;
            try {
                String s = reader.readLine();
                if (s == null)
                    return null;
                sum = Integer.parseInt(s);
                int k = sum;
                if (k > 0) {
                    //reader.close();
                    return sum;
                } else
                   return null;
            } catch (Exception e) {
                return null;
            }
        }
    }

    private static OrganizationType getType(BufferedReader reader, Work.Realization realization){
        //BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        if(realization == Work.Realization.InteractiveWork){
        String str;
        while (true){
            one:
            {
                try {
                    str = reader.readLine();
                    if(str == null)
                        return null;

                    if (str.equalsIgnoreCase("COMMERCIAL")) {
                        //reader.close();
                        return OrganizationType.COMMERCIAL;
                    } else if (str.equalsIgnoreCase("PUBLIC")) {
                        //reader.close();
                        return OrganizationType.PUBLIC;
                    } else if (str.equalsIgnoreCase("TRUST")) {
                        //reader.close();
                        return OrganizationType.TRUST;
                    } else
                        System.out.println(str + " - такого типа элемента не существует." +
                                "\n" + "Попробуйте ввести новое значение:");
                } catch (Exception e) {
                    System.out.println("Аыыы, исключение *происходится* . Попробуйте снова:");
                    break one;
                }
            }
        }
        }
        else {
            String str;
            try {
                str = reader.readLine();
                if(str == null)
                    return null;

                if (str.equalsIgnoreCase("COMMERCIAL")) {
                    //reader.close();
                    return OrganizationType.COMMERCIAL;
                } else if (str.equalsIgnoreCase("PUBLIC")) {
                    //reader.close();
                    return OrganizationType.PUBLIC;
                } else if (str.equalsIgnoreCase("TRUST")) {
                    //reader.close();
                    return OrganizationType.TRUST;
                } else
                    return null;
            } catch (Exception e) {
                return null;
            }
        }

    }

    private static Address getOfficialAddress(BufferedReader reader, Work.Realization realization){

        //BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        if(realization == Work.Realization.InteractiveWork) {
            String str;
            while (true) {
                one:
                {
                    try {
                        str = reader.readLine();
                        if (str == null)
                            return null;

                        if (str.length() > 0 && str.length() <= 15) {
                            //reader.close();
                            return new Address(str);
                        } else if (str.equals("")) {
                            System.out.println("ага, похоже на мой адрес...");
                            System.out.println("значение не должно быть пустым, попробуйте ввести новый адрес:");
                        } else
                            System.out.println("Введен слишком длинный адрес(больше 15 символов). Попробуйте ввести новый адрес:");
                    } catch (Exception e) {
                        System.out.println("Аыыы, повторите");
                        break one;
                    }
                }
            }
        }
        else {
            String str;
            try {
                str = reader.readLine();
                if (str == null)
                    return null;

                if (str.length() > 0 && str.length() <= 15) {
                    //reader.close();
                    return new Address(str);
                } else {
                    return null;
                }
            } catch (Exception e) {
               return null;
            }
        }
    }
}
