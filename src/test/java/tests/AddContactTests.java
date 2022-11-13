package tests;

import config.AppiumConfig;
import models.Auth;
import models.Contact;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import screens.AuthenticationScreen;
import screens.ContactListScreen;

import java.util.Random;

public class AddContactTests extends AppiumConfig {

    @BeforeClass
    public void precondition() {
        new AuthenticationScreen(driver)
                .login(Auth.builder().email("zverevainna87@gmail.com").password("Ii123456!").build());
    }

    @Test
    public void addNewContactSuccess() {
        int i = new Random().nextInt(1000) + 1000;

        Contact contact = Contact.builder()
                .name("Simon" + i)
                .lastname("Wow" + i)
                .email("simon" + i + "@mail.com")
                .phone("123456789123")
                .address("Haifa")
                .description("The best friend").build();

        new ContactListScreen(driver)
                .openContactForm()
                .fillContactForm(contact)
                .submitContactForm()
                .isContactAddedByName(contact.getName(), contact.getLastname())
                .isContactAddedByPhone(contact.getPhone());
    }

    @Test
    public void addNewContactNoDescriptionSuccess() {
        int i = new Random().nextInt(1000) + 1000;

        Contact contact = Contact.builder()
                .name("Simon" + i)
                .lastname("Wow" + i)
                .email("simon" + i + "@mail.com")
                .phone("123456789" + i)
                .address("Haifa")
                .description("").build();

        new ContactListScreen(driver)
                .openContactForm()
                .fillContactForm(contact)
                .submitContactForm()
                .isContactAddedByName(contact.getName(), contact.getLastname())
                .isContactAddedByPhone(contact.getPhone());
    }

    @Test
    public void addNewContactNegativeWrongName() {
        int i = new Random().nextInt(1000) + 1000;

        Contact contact = Contact.builder()
                .name("")
                .lastname("Wow" + i)
                .email("simon" + i + "@mail.com")
                .phone("123456789" + i)
                .address("Haifa")
                .description("").build();

        new ContactListScreen(driver)
                .openContactForm()
                .fillContactForm(contact)
                .submitContactForm()
                .isErrorMessageContainsTextInAlert("name=must not be blank");
    }

    @Test
    public void addNewContactNegativeWrongLastname() {
        int i = new Random().nextInt(1000) + 1000;

        Contact contact = Contact.builder()
                .name("simon")
                .lastname("")
                .email("simon" + i + "@mail.com")
                .phone("123456789" + i)
                .address("Haifa")
                .description("").build();

        new ContactListScreen(driver)
                .openContactForm()
                .fillContactForm(contact)
                .submitContactForm()
                .isErrorMessageContainsTextInAlert("lastName=must not be blank");
    }

    @Test
    public void addNewContactNegativeWrongEmail() {
        int i = new Random().nextInt(1000) + 1000;

        Contact contact = Contact.builder()
                .name("simon")
                .lastname("Wow" + i)
                .email("simon" + i + "mail.com")
                .phone("123456789" + i)
                .address("Haifa")
                .description("").build();

        new ContactListScreen(driver)
                .openContactForm()
                .fillContactForm(contact)
                .submitContactForm()
                .isErrorMessageContainsTextInAlert("email=must be a well-formed email address");
    }

    @Test
    public void addNewContactNegativeWrongPhone() {
        int i = new Random().nextInt(1000) + 1000;

        Contact contact = Contact.builder()
                .name("simon")
                .lastname("Wow" + i)
                .email("simon" + i + "@mail.com")
                .phone("1234")
                .address("Haifa")
                .description("").build();

        new ContactListScreen(driver)
                .openContactForm()
                .fillContactForm(contact)
                .submitContactForm()
                .isErrorMessageContainsTextInAlert("phone=Phone number must contain only digits! And length min 10, max 15!");
    }

    @Test
    public void addNewContactNegativeWrongAddress() {
        int i = new Random().nextInt(1000) + 1000;

        Contact contact = Contact.builder()
                .name("simon")
                .lastname("Wow" + i)
                .email("simon" + i + "@mail.com")
                .phone("123456789" + i)
                .address("")
                .description("").build();

        new ContactListScreen(driver)
                .openContactForm()
                .fillContactForm(contact)
                .submitContactForm()
                .isErrorMessageContainsTextInAlert("address=must not be blank");
    }

    @AfterClass
    public void postCondition() {
        new ContactListScreen(driver)
                .logout();

    }
}