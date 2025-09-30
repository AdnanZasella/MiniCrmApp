package se.chasacademy;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.*;


public class Customer {




    private final String id;


    private String name;


    private final Set<String> emails = new LinkedHashSet<>();


    private final Set<String> tags = new LinkedHashSet<>();


    private final List<String> notes = new ArrayList<>();


    public Customer(String id, String name) {
        this.id = id;
        this.name = name;
    }




    public String id() { return id; }


    public String name() { return name; }


    public void setName(String name) { this.name = Objects.requireNonNull(name).trim(); }


    public Set<String> emails() { return emails; }


    public Set<String> tags() { return tags; }


    public List<String> notes() { return notes; }



    public boolean addEmail(String email) {
        String e = email.trim();
        if (e.isEmpty()) return false;
        return emails.add(e);
    }


    public boolean removeEmail(String email) {
        return emails.remove(email.trim());
    }


    public boolean addTag(String tag) {
        String t = tag.trim();
        if (t.isEmpty()) return false;
        return tags.add(t);
    }


    public void addNote(String note) {
        notes.add(note);
    }

    public boolean removeTag(String tag) {
        return tags.remove(tag.trim());
    }

    public boolean removeNote(String note) {
        return notes.remove(note);
    }


    @Override
    public String toString() {
        return "Customer{id='" + id + "', name='" + name + "', emails=" + emails +
                ", tags=" + tags + ", notes=" + notes + "}";
    }
}
