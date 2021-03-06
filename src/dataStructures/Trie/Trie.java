package dataStructures.Trie;


import com.google.gson.internal.LinkedTreeMap;
import dataStructures.array.Array;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Trie {
    private TrieRoot root;
    private int numWords = 3;

    public Trie(){
        this.root = null;
    }

    public void insert(String paraula){
        char[] word = paraula.toLowerCase().toCharArray();
        int indexWord = 0;
        int lengthWord = word.length;

        if (root == null) {
            root = new TrieRoot();
            if (lengthWord == 1) {
                root.addSon(new TrieNode(word[indexWord],true,1));
            }
            else {
                TrieNode fill = new TrieNode(word[indexWord],false,1);
                root.addSon(fill);
                insertI(word,++indexWord,lengthWord,fill);
            }
        }
        else {
            int sizeSons = root.getSons().size();
            for (int i = 0; i < sizeSons; i++) {
                if (((TrieNode) root.getSons().get(i)).getLetter() == word[indexWord]) {
                    ((TrieNode) root.getSons().get(i)).addNumOfWords();
                    insertI(word,++indexWord,lengthWord,(TrieNode) root.getSons().get(i));
                    return;
                }
            }

            // Si arriba aquí, vol dir que la lletra de la paraula no es troba emmagatzemada com a fill del TrieNode father
            if (lengthWord == 1) {
                root.addSon(new TrieNode(word[indexWord],true,1));
            }
            else {
                TrieNode newSon = new TrieNode(word[indexWord],false,1);
                root.addSon(newSon);
                insertI(word,++indexWord,lengthWord,newSon);
            }
        }
    }

    private void insertI(char[] word, int indexWord, int lengthWord, TrieNode father){
        if (indexWord < lengthWord) {
            int sizeSons = father.getSons().size();

            for (int i = 0; i < sizeSons; i++) {
                if (father.getSons().get(i) instanceof TrieNode) {
                    if (((TrieNode) father.getSons().get(i)).getLetter() == word[indexWord]) {
                        ((TrieNode) father.getSons().get(i)).addNumOfWords();
                        insertI(word,++indexWord,lengthWord,(TrieNode) father.getSons().get(i));
                        return;
                    }
                }
            }

            // Si arriba aquí, vol dir que la lletra de la paraula no es troba emmagatzemada com a fill del TrieNode father
            for (int i = indexWord; i < lengthWord; i++) {
                TrieNode newSon;
                if (i < lengthWord - 1) {
                    newSon = new TrieNode(word[i],false,1);
                }
                else {
                    newSon = new TrieNode(word[i],true,1);
                }
                father.addSon(newSon);
                father = newSon;
            }
        }
        else {
            father.setEndOfWord(true);
        }
    }

    public void printStructure() {
        if (root != null) {
            System.out.print("[Root]: null, [Childs]: ");
            int size = root.getSons().size();
            for (int i = 0; i < size; i++) {
                if (root.getSons().get(i) instanceof TrieNode) {
                    System.out.print(((TrieNode) root.getSons().get(i)).getLetter() + " ");
                }
                else {
                    if (root.getSons().get(i) instanceof LinkedTreeMap) {
                        System.out.println(((LinkedTreeMap) root.getSons().get(i)).get("letter"));
                    }
                }
            }
            System.out.println();
            for (int i = 0; i < size; i++) {
                if (root.getSons().get(i) instanceof TrieNode) {
                    printI((TrieNode) root.getSons().get(i));
                }
                else {
                    if (root.getSons().get(i) instanceof LinkedTreeMap) {
                        printI2(((LinkedTreeMap) root.getSons().get(i)));
                    }
                }
            }
        }
    }

    private void printI(TrieNode father) {
        System.out.print("[Father]: " + father.getLetter() + ", [Childs]: ");
        if (!father.isEndOfWord()) {
            int numberOfSons = father.getSons().size();
            for (int i = 0; i < numberOfSons; i++) {
                TrieNode newFather = (TrieNode) father.getSons().get(i);
                System.out.print(newFather.getLetter() + " ");
            }
            System.out.println();
            for (int i = 0; i < numberOfSons; i++) {
                TrieNode newFather = (TrieNode) father.getSons().get(i);
                printI(newFather);
            }
        }
        else {
            if (father.getSons().size() > 0) {
                int numberOfSons = father.getSons().size();
                for (int i = 0; i < numberOfSons; i++) {
                    TrieNode newFather = (TrieNode) father.getSons().get(i);
                    System.out.print(newFather.getLetter() + " ");
                }
                System.out.println();
                for (int i = 0; i < numberOfSons; i++) {
                    TrieNode newFather = (TrieNode) father.getSons().get(i);
                    printI(newFather);
                }
            }
            else {
                System.out.print("-");
                System.out.println();
            }
        }
    }

    private void printI2(LinkedTreeMap map) {
        System.out.print("[Father]: " + map.get("letter") + ", [Childs]: ");
        if (!(boolean)map.get("endOfWord")) {
            int numberOfSons = ((ArrayList)((LinkedTreeMap)map.get("sons")).get("elements")).size();
            for (int i = 0; i < numberOfSons; i++) {
                LinkedTreeMap newFather = (LinkedTreeMap) ((ArrayList)((LinkedTreeMap)map.get("sons")).get("elements")).get(i);
                System.out.print(newFather.get("letter") + " ");
            }
            System.out.println();
            for (int i = 0; i < numberOfSons; i++) {
                LinkedTreeMap newFather = (LinkedTreeMap) ((ArrayList)((LinkedTreeMap)map.get("sons")).get("elements")).get(i);
                printI2(newFather);
            }
        }
        else {
            if (((LinkedTreeMap)map.get("sons")).size() > 0) {
                int numberOfSons = ((ArrayList)((LinkedTreeMap)map.get("sons")).get("elements")).size();
                for (int i = 0; i < numberOfSons; i++) {
                    LinkedTreeMap newFather = (LinkedTreeMap) ((ArrayList)((LinkedTreeMap)map.get("sons")).get("elements")).get(i);
                    System.out.print(newFather.get("letter") + " ");
                }
                System.out.println();
                for (int i = 0; i < numberOfSons; i++) {
                    LinkedTreeMap newFather = (LinkedTreeMap) ((ArrayList)((LinkedTreeMap)map.get("sons")).get("elements")).get(i);
                    printI2(newFather);
                }
            }
            else {
                System.out.print("-");
                System.out.println();
            }
        }
    }


    /** TESTING **/
    public static void main(String[] args) {
        Trie trie = new Trie();

        System.out.println("Testing del Trie");
        int cas = 0;
        while (cas != 5) {
            System.out.println("\t1. Inserir paraula\n\t2. AutoCompletar\n\t3. Buscar paraula\n\t4. Eliminar paraula\n\t5. Sortir\nOpcio: ");
            Scanner sc = new Scanner(System.in);
            try {
                cas = sc.nextInt();
                switch (cas) {
                    case 1:
                        System.out.println("Quina paraula vols afegir? ");
                        String word = sc.next();
                        trie.insert(word);
                        break;

                    case 2:
                        System.out.println("Escriu una paraula: ");
                        word = sc.next();
                        Array<String> matchings = trie.getMatchingWords(word);
                        int trobades = matchings.size();

                        if (trobades == 0) {
                            System.out.println("No s'han trobat paraules que continguin '" + word + "'");
                        }
                        else {
                            for (int i = 0; i < trobades; i++) {
                                System.out.println(matchings.get(i));
                            }
                        }
                        break;

                    case 3:
                        System.out.println("Escriu una paraula: ");
                        word = sc.next();
                        boolean found = trie.search(word);
                        if (found) {
                            System.out.println("S'ha trobat la paraula");
                        }
                        else {
                            System.out.println("No s'ha trobat la paraula");
                        }
                        break;

                    case 4:
                        System.out.println("Escriu una paraula: ");
                        word = sc.next();
                        trie.deleteWord(word);
                        System.out.println("S'ha eliminat satisfactoriament!");
                        break;

                    case 5:
                        break;

                    default:
                        System.out.println("Opcio invalida!");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Numbers only");
            }
        }
    }

    public boolean search(String word) {
        char[] wordArray = word.toLowerCase().toCharArray();

        if (root == null) {
            return false;
        }
        else {
            int rootSons = root.getSons().size();
            for (int i = 0; i < rootSons; i++) {
                if (root.getSons().get(i) instanceof TrieNode) {
                    if (((TrieNode) root.getSons().get(i)).getLetter() == wordArray[0]) {
                        return searchI(wordArray,1, (TrieNode) root.getSons().get(i));
                    }
                }
            }
            return false;
        }
    }

    private boolean searchI(char[] word, int indexWord, TrieNode father) {
        if (indexWord == word.length && father.isEndOfWord()) {
            return true;
        }
        else if (indexWord == word.length && !father.isEndOfWord()) {
            return false;
        }
        else {
            int sonsSize = father.getSons().size();

            for (int i = 0; i < sonsSize; i++) {
                if (father.getSons().get(i) instanceof TrieNode) {
                    if (((TrieNode) father.getSons().get(i)).getLetter() == word[indexWord]) {
                        return searchI(word,++indexWord, (TrieNode) father.getSons().get(i));
                    }
                }
            }
            return false;
        }
    }


    public Array<String> getMatchingWords(String word) {
        char[] wordArray = word.toLowerCase().toCharArray();
        Array<String> matchingWords = new Array<>();

        if (root == null) {
            return matchingWords;
        }
        else {
            int sonSize = root.getSons().size();
            for (int i = 0; i < sonSize; i++) {
                if (root.getSons().get(i) instanceof TrieNode) {
                    if (((TrieNode) root.getSons().get(i)).getLetter() == wordArray[0]) {
                        char[] actualWord = new char[1];
                        actualWord[0] = wordArray[0];
                        getMatchingWordsI(matchingWords,wordArray,actualWord,1,(TrieNode) root.getSons().get(i));
                    }
                }
            }
        }
        return matchingWords;
    }

    private void getMatchingWordsI(Array<String> matchingWords, char[] word, char[] actualWord, int indexWord, TrieNode father) {
        if (father.isEndOfWord() && word.length == indexWord) {
            String newWord = String.copyValueOf(actualWord);
            matchingWords.add(newWord);
            // A partir de aquí miramos si hay más combinaciones que tengan como principio de palabra la palabra introducida
            getMatchingAux(matchingWords,newWord,father);
        }
        else if (father.hasSons() && indexWord < word.length) {
            int sonsSize = father.getSons().size();
            for (int i = 0; i < sonsSize; i++) {
                if (father.getSons().get(i) instanceof TrieNode) {
                    if (((TrieNode) father.getSons().get(i)).getLetter() == word[indexWord]) {
                        getMatchingWordsI(matchingWords, word, (String.copyValueOf(actualWord) + ((TrieNode) father.getSons().get(i)).getLetter()).toCharArray(), ++indexWord, (TrieNode) father.getSons().get(i));
                        indexWord--;
                    }
                }
            }
        }
        else if (!father.isEndOfWord() && word.length == indexWord) {
            if (matchingWords.size() < numWords) {
                getMatchingAux(matchingWords,String.copyValueOf(actualWord),father);
            }
        }
    }

    private void getMatchingAux(Array<String> matchingWords, String newWord, TrieNode father) {
        boolean end = false;
        if (father.hasSons()) {
            int sonsSize = father.getSons().size();
            int i = 0;
            while (i < sonsSize && !end)  {
                if (father.getSons().get(i) instanceof TrieNode) {
                    if (((TrieNode) father.getSons().get(i)).isEndOfWord()) {
                        matchingWords.add(newWord + ((TrieNode) father.getSons().get(i)).getLetter());
                    }
                    if (((TrieNode) father.getSons().get(i)).hasSons()) {
                        getMatchingAux(matchingWords,newWord + ((TrieNode) father.getSons().get(i)).getLetter(),(TrieNode) father.getSons().get(i));
                    }
                    if (matchingWords.size() >= numWords) {
                        end = true;
                    }
                    i++;
                }
            }
        }
    }

    public void deleteWord(String word) {
        boolean found = search(word);
        if (found) {
            int sonsSize = root.getSons().size();
            int i = 0;
            while (i < sonsSize) {
                if (word.charAt(0) == ((TrieNode)root.getSons().get(i)).getLetter() && ((TrieNode)root.getSons().get(i)).getNumOfWords() < 2) {
                    deleteI(word.toCharArray(), 1, (TrieNode) root.getSons().get(i), true);
                    root.getSons().remove(i);
                } else if (word.charAt(0) == ((TrieNode)root.getSons().get(i)).getLetter() && ((TrieNode)root.getSons().get(i)).getNumOfWords() > 1) {
                    ((TrieNode) root.getSons().get(i)).lessNumOfWords();
                    deleteI(word.toCharArray(), 1, (TrieNode) root.getSons().get(i), false);
                }
                i++;
            }
        }
        else {
            System.out.println("[ERR] - No s'ha trobat l'usuari amb username " + word + " a l'estructura");
        }
    }

    private void deleteI(char[] word, int indexWord, TrieNode father, boolean delete) {
        // Quiere decir que se tienen que borrar todos los nodos de la estructura hasta llegar al final de la palabra
        if (delete) {
            if (indexWord < word.length) {
                // Al entrar en este if ya se ha comprovado que solo tiene un hijo porque el padre solo tiene una palabra
                deleteI(word, ++indexWord, (TrieNode) father.getSons().get(0), delete);
                father.getSons().remove(0);
            }
        }
        else {
            if (indexWord == word.length) {
                if (father.isEndOfWord() && father.hasSons()) {
                    father.setEndOfWord(false);
                }
            }
            else {
                for (int i = 0; i < father.getSons().size(); i++) {
                    if (((TrieNode)father.getSons().get(i)).getLetter() == word[indexWord]) {
                        ((TrieNode) father.getSons().get(i)).lessNumOfWords();
                        if (((TrieNode) father.getSons().get(i)).getNumOfWords() == 0) {
                            father.getSons().remove(i);
                        }
                        else {
                            deleteI(word, ++indexWord, (TrieNode) father.getSons().get(i), false);
                        }
                    }
                }
            }
        }
    }

    public void setNumWords(int numWords) {
        this.numWords = numWords;
    }

    public int getNumWords() {
        return numWords;
    }

    public TrieRoot getRoot() {
        return this.root;
    }

    public void setRoot(TrieRoot root) {
        this.root = root;
    }

    public static TrieRoot setImportedInfo(TrieRoot rootOld) {
        int sonsSize = rootOld.getSons().size();
        TrieRoot rootNew = new TrieRoot();
        for (int i = 0; i < sonsSize; i++) {
            boolean endOfWord = (boolean)((LinkedTreeMap)rootOld.getSons().get(i)).get("endOfWord");
            int numOfWords = (int)Math.round((double)((LinkedTreeMap)rootOld.getSons().get(i)).get("numOfWords"));
            char letter = ((String)((LinkedTreeMap)rootOld.getSons().get(i)).get("letter")).charAt(0);
            TrieNode newNode = new TrieNode(letter, endOfWord, numOfWords);
            newNode = setImportedInfoI(newNode,(ArrayList)((LinkedTreeMap)((LinkedTreeMap)rootOld.getSons().get(i)).get("sons")).get("elements"));
            rootNew.addSon(newNode);
        }
        return rootNew;
    }

    private static TrieNode setImportedInfoI(TrieNode father, ArrayList sons) {
        int sonsSize = sons.size();
        for (int i = 0; i < sonsSize; i++) {
            boolean endOfWord = (boolean)((LinkedTreeMap)sons.get(i)).get("endOfWord");
            int numOfWords = (int)Math.round((double)((LinkedTreeMap)sons.get(i)).get("numOfWords"));
            char letter = ((String)((LinkedTreeMap)sons.get(i)).get("letter")).charAt(0);
            TrieNode newNode = new TrieNode(letter, endOfWord, numOfWords);
            newNode = setImportedInfoI(newNode, (ArrayList)((LinkedTreeMap)((LinkedTreeMap)sons.get(i)).get("sons")).get("elements"));
            father.addSon(newNode);
        }
        return father;
    }

    @Override
    public String toString() {
        return root.toString();
    }
}
