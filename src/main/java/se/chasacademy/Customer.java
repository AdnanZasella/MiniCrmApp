package se.chasacademy;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.*;

/**
 * Data holder for one customer. Uses sets to avoid duplicates.
 */
public class Customer {

    // --- Fields ---

    /** immutable id, e.g. C-0004 */
    private final String id;

    /** mutable display name */
    private String name;

    /** unique emails, preserves insertion order */
    private final Set<String> emails = new LinkedHashSet<>();

    /** unique tags, preserves insertion order */
    private final Set<String> tags = new LinkedHashSet<>();

    /** free-text notes (keeps all entries) */
    private final List<String> notes = new ArrayList<>();

    // --- Constructor ---

    public Customer(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // --- Getters / tiny setters ---

    /** returns id (read-only) */
    public String id() { return id; }

    /** returns current name */
    public String name() { return name; }

    /** trims and sets name (cannot be null) */
    public void setName(String name) { this.name = Objects.requireNonNull(name).trim(); }

    /** live view of emails set */
    public Set<String> emails() { return emails; }

    /** live view of tags set */
    public Set<String> tags() { return tags; }

    /** live view of notes list */
    public List<String> notes() { return notes; }

    // --- Mutators (add/remove helpers) ---

    /** add email if not empty/duplicate */
    public boolean addEmail(String email) {
        String e = email.trim();
        if (e.isEmpty()) return false;
        return emails.add(e);
    }

    /** remove email if present */
    public boolean removeEmail(String email) {
        return emails.remove(email.trim());
    }

    /** add tag if not empty/duplicate */
    public boolean addTag(String tag) {
        String t = tag.trim();
        if (t.isEmpty()) return false;
        return tags.add(t);
    }

    /** append note as-is (can be empty) */
    public void addNote(String note) {
        notes.add(note);
    }

    public boolean removeTag(String tag) {
        return tags.remove(tag.trim());
    }

    public boolean removeNote(String note) {
        return notes.remove(note);
    }

    // --- Debug print ---

    @Override
    public String toString() {
        return "Customer{id='" + id + "', name='" + name + "', emails=" + emails +
                ", tags=" + tags + ", notes=" + notes + "}";
    }
}
