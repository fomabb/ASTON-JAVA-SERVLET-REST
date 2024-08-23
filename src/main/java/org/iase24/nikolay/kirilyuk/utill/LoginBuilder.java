package org.iase24.nikolay.kirilyuk.utill;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
public class LoginBuilder {

    public List<String> createRandomData() {

        return Arrays.asList(
                "John", "Alice", "Bob", "Carol", "David", "Emma", "Michael", "Sophia", "James", "Olivia",
                "William", "Isabella", "Benjamin", "Mia", "Lucas", "Charlotte", "Henry", "Amelia", "Alexander", "Evelyn",
                "Sebastian", "Harper", "Elijah", "Ava", "Matthew", "Scarlett", "Daniel", "Victoria", "Jack", "Liam",
                "Samuel", "Grace", "Christopher", "Zoe", "Joseph", "Madison", "Andrew", "Layla", "Joshua", "Riley",
                "Gabriel", "Aria", "Dylan", "Nora", "Logan", "Lily", "Ryan", "Chloe", "Caleb", "Ellie",
                "Nathan", "Mila", "Isaac", "Hannah", "Ethan", "Aubrey", "Connor", "Addison", "Oliver", "Eleanor",
                "Owen", "Penelope", "Adam", "Lillian", "Evan", "Stella", "Luke", "Paisley", "Aiden", "Savannah",
                "Landon", "Hazel", "Aaron", "Aurora", "Zachary", "Aaliyah", "Tyler", "Samantha", "Julian", "Sarah",
                "Christian", "Claire", "Hudson", "Skylar", "Lincoln", "Arianna", "Carter", "Genesis", "Nicholas", "Violet",
                "Hunter", "Naomi", "Gavin", "Luna", "Brayden", "Maya", "Cole", "Nevaeh", "Jordan", "Brooklyn",
                "Isaiah", "Bella", "Mason", "Elena", "Jayden", "Ariana", "Levi", "Gabriella", "Angel", "Allison",
                "Jaxon", "Madelyn", "Dominic", "Alice", "Austin", "Alyssa", "Elias", "Eliana", "Jason", "Peyton",
                "Nathaniel", "Savanna", "Jonathan", "Ruby", "Adrian", "Eva", "Charles", "Lucy", "Thomas", "Kennedy",
                "Nolan", "Madeline", "Parker", "Autumn", "Josiah", "Faith", "Chase", "Jade", "Robert", "Serenity",
                "Grayson", "Lydia", "Cameron", "Delilah", "Wyatt", "Josephine", "Asher", "Maria", "Jeremiah", "Cora",
                "Axel", "Piper", "Colton", "Alyson", "Bentley", "Sophie", "Micah", "Quinn", "Miles", "Willow",
                "Vincent", "Rylee", "Declan", "Mackenzie", "Maxwell", "Brooke", "Grant", "Melanie", "Ezra", "Ivy",
                "Tyson", "London", "Harrison", "Hadley", "Silas", "Emery", "Gage", "Jordyn", "Emmett", "Eden",
                "Lukas", "Emerson", "Damian", "Charlie", "Beau", "Adeline", "Ryder", "Athena", "Jasper", "Alaina",
                "Theo", "Aubree", "Max", "Daisy", "Sawyer", "Blakely", "Kingston", "Morgan", "Camden", "Reagan",
                "Brody", "Summer", "Amir", "Payton", "Rowan", "Juliette", "Ryker", "Lucia", "Kaleb", "Isabel",
                "Enzo", "Charlie", "Jude", "Amaya", "Anderson", "Annabelle", "Tanner", "Valentina", "Lane", "Gemma",
                "Bennett", "Arabella", "Xavier", "Rose", "Simon", "Jocelyn", "Zane", "Paige", "Ronan", "Teagan",
                "Everett", "Kinsley", "Beckett", "Alana", "Milo", "Julianna", "Sullivan", "Brielle", "Finley", "Kendall",
                "Jace", "Emilia", "Blake", "Maeve", "Paxton", "Elise", "Ashton", "Sienna", "Graham", "Bailey",
                "Ellis", "Lila", "Tristan", "Alina", "Jake", "Catalina", "Griffin", "Nova", "Cash", "Adalyn",
                "Kyle", "Keira", "Brooks", "Camila", "Holden", "Adalynn", "Spencer", "Cecilia", "Cohen", "Freya",
                "Kane", "Miriam", "Paolo", "Emersyn", "Shane", "Briana", "Brandon", "Laura", "Leo", "Alexa",
                "Carlos", "Raelynn", "Julio", "Rosalie", "Leonardo", "Josie", "Dante", "Carly", "Victor", "Alivia",
                "Marco", "Jenna", "Zion", "Aniyah", "Noel", "Aurora", "Romeo", "Harley", "Hugo", "Makayla",
                "Kaden", "Malia", "Kendrick", "Hayden", "Kobe", "Eloise", "Nico", "Camille", "Marcelo", "Raven",
                "Archer", "Esmeralda", "Joaquin", "Lia", "Pedro", "Evie", "Derrick", "Ophelia", "Javier", "Holly",
                "Mitchell", "Monica", "Scott", "Alison", "Fabian", "Jane", "Josue", "Maddison", "Alden", "Lorelei",
                "Cruz", "Evangeline", "Kian", "Raegan", "Malcolm", "Daphne", "Zander", "Cassidy", "Zeke", "Annalise",
                "Sage", "Heaven", "Kyler", "Nylah", "Dexter", "Adelaide"
        );
    }

    public String generateRandomPassword(Random random, int minLength, int maxLength) {
        int passwordLength = random.nextInt((maxLength - minLength) + 1) + minLength; // Генерация длины пароля
        StringBuilder password = new StringBuilder(passwordLength);

        for (int i = 0; i < passwordLength; i++) {
            int randomDigit = random.nextInt(10); // Генерация случайной цифры от 0 до 9
            password.append(randomDigit);
        }

        return password.toString();
    }
}
