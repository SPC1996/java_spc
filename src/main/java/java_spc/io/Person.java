package java_spc.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.prefs.Preferences;

import java_spc.util.Resource;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.Serializer;

public class Person {
    private String first, last;

    public Person(String first, String last) {
        this.first = first;
        this.last = last;
    }

    public Person(Element person) {
        this.first = person.getFirstChildElement("first").getValue();
        this.last = person.getFirstChildElement("last").getValue();
    }

    public Element getXML() {
        Element person = new Element("person");
        Element firstName = new Element("first");
        firstName.appendChild(first);
        Element lastName = new Element("last");
        lastName.appendChild(last);
        person.appendChild(firstName);
        person.appendChild(lastName);
        return person;
    }

    public String toString() {
        return first + " " + last;
    }

    public static void format(OutputStream out, Document doc) throws Exception {
        Serializer serializer = new Serializer(out, "utf-8");
        serializer.setIndent(4);
        serializer.setMaxLength(60);
        serializer.write(doc);
        serializer.flush();
    }

    public static List<Person> getPeople(String fileName) throws Exception {
        List<Person> people = new ArrayList<>();
        Document document = new Builder().build(new File(fileName));
        Elements elements = document.getRootElement().getChildElements();
        for (int i = 0; i < elements.size(); i++) {
            people.add(new Person(elements.get(i)));
        }
        return people;
    }

    public static void main(String[] args) throws Exception {
        List<Person> people = Arrays.asList(
                new Person("Pc", "S"),
                new Person("King", "Rod"),
                new Person("Lily", "Mike"));
        System.out.println(people);
        Element root = new Element("people");
        for (Person person : people) {
            root.appendChild(person.getXML());
        }
        Document document = new Document(root);
        format(System.out, document);
        format(new BufferedOutputStream(new FileOutputStream(Resource.pathToSource("file/people.xml"))), document);
        people = getPeople(Resource.pathToSource("file/people.xml"));
        System.out.println(people);
        PreferenceDemo.preference();
    }
}

class PreferenceDemo {
    public static void preference() throws Exception {
        Preferences prefs = Preferences.userNodeForPackage(PreferenceDemo.class);
        prefs.put("location", "Oz");
        prefs.put("Footwear", "Ruby slippers");
        prefs.putInt("Companions", 4);
        prefs.putBoolean("Are there witchs", true);
        int usageCount = prefs.getInt("usageCount", 0);
        usageCount++;
        prefs.putInt("usageCount", usageCount);
        for (String key : prefs.keys()) {
            System.out.println(key + ": " + prefs.get(key, null));
        }

    }
}
