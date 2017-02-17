package mainPackage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Word
{
	protected String OriginalCode; 									//The original code inputed (e.g: "0224180415")
	protected int Letters;											//The number of letters the word has
	protected int[] Code;											//An array (size = letters) that has each int of the code
	protected char[] DecodedLetters;								//An Array (size = letters) that has the decoded letters of the code (or a ? where it isn't solved)
	protected boolean Found;										//A boolean to see if the word is complete or not
	protected boolean[] HasNumber;									//A boolean array (size = 26) that says whether the word has the NUMBER (not the letter of the alphabet)
	protected boolean[] LetterFound = new boolean[26];				//A boolean array (size = 26) to say whether that letter is found (
	ArrayList<String> PossibleSolutions = new ArrayList<String>();	//An ArrayList of all the possible solutions
	String FoundWord;												//The string of the word after it has been found
	
	Word(String CodedWord)
	{
		this.OriginalCode = CodedWord;
		this.Letters = LettersInWord(OriginalCode);
		this.Code = SeparateNumbers(OriginalCode, Letters);
		DecodedLetters = new char[Letters];
		Arrays.fill(DecodedLetters, '?');
		this.Found = false;
		HasNumber = CheckNumbers(Code);
	}	
	
	@SuppressWarnings("unchecked")
	public void Copy(Word OtherWord)
	{
		this.OriginalCode = OtherWord.OriginalCode + "";
		this.Letters = OtherWord.Letters;
		this.Code = OtherWord.Code.clone();
		this.DecodedLetters = OtherWord.DecodedLetters.clone();
		this.Found = OtherWord.Found;
		this.HasNumber = OtherWord.HasNumber.clone();
		this.LetterFound = OtherWord.LetterFound.clone();
		this.PossibleSolutions = (ArrayList<String>) OtherWord.PossibleSolutions.clone();
		this.FoundWord = OtherWord.FoundWord + "";
	}
	
	public int UniqueBlanks()
	{
		int Blanks = 0;
		for (int i = 0; i < 26; i++)
		{
			if (HasNumber[i] && !LetterFound[i])
			{
				Blanks++;
			}
		}
		return Blanks;
	}
	
	public static boolean CheckWord(String TestingWord) throws IOException
	{
		BufferedReader[] Input = new BufferedReader[8];
        String ReadWord;
        
        for (int i = 0; i < Input.length; i++)
		{
			Input[i] =  new BufferedReader(new FileReader(System.getenv("APPDATA") + "/.CodeCracker/Words" + (i+3) + ".txt"));
		}
        
        int WordLength = TestingWord.length();
        if (WordLength > 10 || WordLength < 1)
        {
        	WordLength = 9;
        }
        
        while ((ReadWord = Input[WordLength - 3].readLine()) != null) 
        {
            if (ReadWord.indexOf(TestingWord) != -1) 
            {
                return true;
            }
        }
        for (int i = 0; i < Input.length; i++)
		{
        	Input[i].close();
		}
        return false;
	}
	
	public void UpdateLetterDecoding(int LetterCode, char RealLetter)
	{
		for (int i = 0; i < Letters; i++)
		{
			if (Code[i] == LetterCode)
			{
				DecodedLetters[i] = RealLetter;
				LetterFound[LetterCode-1] = true;
			}
		}
		UpdateIfFound();
	}
	
	
	private void UpdateIfFound()
	{
		boolean HasUnkownLetter = false;
		String Word = "";
		for (int i = 0; i < Letters; i++)
		{
			if (DecodedLetters[i] == '?')
			{
				HasUnkownLetter = true;
			}else
			{
				Word = Word + DecodedLetters[i];
			}
		}
		if (!HasUnkownLetter)
		{
			FoundWord = Word + "";
			Found = true;
		}
	}
	
	public String ToCurrentCode()
	{
		String CurrentCode = "";
		for (int i = 0; i < Letters; i++)
		{
			if (DecodedLetters[i] != '?')
			{
				CurrentCode = CurrentCode + DecodedLetters[i];
			}else
			{
				if (Code[i] < 10)
				{
					CurrentCode = CurrentCode + "0" + Code[i];
				}else
				{
					CurrentCode = CurrentCode + Code[i];
				}
			}
		}
		return CurrentCode;
	}
	
	public String ToSearchableWord()
	{
		boolean[] LettersDone = new boolean[Letters];
		char[] FixedLetters = new char[Letters];
		String FixedWord = "";
		int CurrentBlank = 1;
		for (int i = 0; i < Letters; i++)
		{
			if (Character.isAlphabetic(DecodedLetters[i]))
			{
				FixedLetters[i] = DecodedLetters[i];
			}else if (!LettersDone[i])
			{
				FixedLetters[i] = ("" + CurrentBlank).charAt(0);
				LettersDone[i] = true;
				for (int j = 0; j < Letters; j++)
				{
					if (Code[j] == Code[i] && j != i)
					{
						FixedLetters[j] = ("" + CurrentBlank).charAt(0);
						LettersDone[j] = true;
					}
				}
				CurrentBlank++;
			}
		}
		for (int i = 0; i < Letters; i++)
		{
			FixedWord = FixedWord + FixedLetters[i];
		}
		
		return FixedWord.toLowerCase();
	}
	
	public int FindPossibleSolutions(int Loops, String Word, int RecursiveLevel, boolean[] LettersAvailable, char[] Alphabet, int Solutions) throws IOException
	{
		for (int i = 0; i < 26; i++)
		{
			String NewWord = Word.toUpperCase();
			if (LettersAvailable[i])
			{
				LettersAvailable[i] = false;
				char ReplaceNum = (RecursiveLevel + "").charAt(0);
				NewWord = NewWord.replace(ReplaceNum, Alphabet[i]);
				NewWord = NewWord.toLowerCase();
				if (Loops > 1)
				{
					Loops--;
					Solutions = FindPossibleSolutions(Loops, NewWord, RecursiveLevel+1, LettersAvailable, Alphabet, Solutions);
					Loops++;
				}else
				{
					if (CheckWord(NewWord))
					{
						Solutions++;
						PossibleSolutions.add(NewWord);
					}
				}
				LettersAvailable[i] = true;
			}else
			{
				continue;
			}
		}
		return Solutions;
	}
	
	
	
//Private	
	private int LettersInWord(String Word)
	{
		if (Word.length()%2 == 0)
		{
			int LettersInWord = Word.length()/2;
			return LettersInWord;
		}else
		{
			return -1;
		}
		
	}
	private int[] SeparateNumbers(String Word, int Letters)
	{
		int[] Code = new int[Letters];
		for (int i = 0, j = 0; i < Letters; i++, j = j + 2)
		{
			String NumberString = "" + Word.charAt(j) + Word.charAt(j + 1);
			int NumberInt = Integer.parseInt(NumberString);
			Code[i] = NumberInt;
		}
		return Code;
	}
	private boolean[] CheckNumbers(int[] Code)
	{
		boolean[] HasNumbers = new boolean[26];
		for (int i = 0; i < Code.length; i++)
		{
			HasNumbers[(Code[i]-1)] = true;
		}
		return HasNumbers;
	}
}
