package ru.home.srvmsg.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "ADDRESSBOOK")
public class AddressBook {

    @Id
    @Column(name = "ADDRESSBOOK_ID")
    @SequenceGenerator(name = "pk_sequence", sequenceName = "addressbook_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
    private Integer addressbook_id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "CONTACT_ID")
    private User contact;

    public AddressBook() {
    }

    public Integer getAddressbook_id() {
        return addressbook_id;
    }

    public void setAddressbook_id(Integer addressbook_id) {
        this.addressbook_id = addressbook_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getContact() {
        return contact;
    }

    public void setContact(User contact) {
        this.contact = contact;
    }
}
