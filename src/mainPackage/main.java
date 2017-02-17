package mainPackage;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;




public class main 
{
	static Scanner keyboard = new Scanner(System.in).useDelimiter("\\n");
	static final char[] Alphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
	static boolean LettersAvailable[] = new boolean[26];
	
	public static void CreateHtmlResults(int GridHeight, int GridWidth, int[][] Grid, char NumToLetterCode[], String PuzzleNote) throws FileNotFoundException
	{
		PrintWriter Output = new PrintWriter(System.getenv("APPDATA") + "/.CodeCracker/Results.html");
		
		Output.println("<!DOCTYPE html>");
		Output.println("<html>");
		Output.println("<head>");
		Output.println("<title>Results for Code Cracker</title>");
		Output.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>");
		Output.println("<link href='https://fonts.googleapis.com/css?family=Patrick+Hand' rel='stylesheet'>");
		Output.println("<style type='text/css'>");
		Output.println("body { background: #e3cd73; color: #000000; margin-right: 20px; margin-left: 20px; font-size: 14px; font-family: Arial, sans-serif, sans; }");
		Output.println("h1 { color: #000000; font-size: 36px; text-align: center; }");
		Output.println("h2 { text-align: center; font-size: 20px; }");
		Output.println("table.result { border: 1px solid #000000; margin-left: auto; margin-right: auto; padding: 0px; border-spacing: 0px; font-family: sans; font-size: 170%;}");
		Output.println("table.result td { border: 1px solid #000000; padding: 0px; background: #ffffff; text-align: center; width: 32px; height: 30px; font-family: 'Patrick Hand'; line-height: 0%}");
		Output.println("table tr td.blank {background: #000000;}");
		Output.println("div.small{ font-size: 55%; text-align: left; line-height: 100%; position: relative; bottom:9px; left:1px;}");
		Output.println("div.regular{ position: relative; bottom:4px;}");
		Output.println("</style>");
		Output.println("</head>");
		Output.println("<body>");
		if (PuzzleNote == null)
		{
			Output.println("<h1>Results</h1>");
		}else
		{
			Output.println("<h1>" + PuzzleNote + "</h1>");
		}
		Output.println("<table class='result'>");
		for (int i = 0; i < GridHeight; i++)
		{
			Output.println("<tr>");
			for (int j = 0; j < GridWidth; j++)
			{
				if (Grid[i][j] == 0)
				{
					Output.println("<td class = 'blank'></td>");
				}else
				{
					Output.println("<td><div class = 'small'>" + Grid[i][j] + "</div><br><div class = 'regular'>" + (NumToLetterCode[Grid[i][j]-1] + "").toUpperCase() +"</div></td>");
				}
			}
			Output.println("</tr>");
		}
		Output.println("</table>");
		Output.println("<h2>Letters:</h2>");
		Output.println("<table class='result'><tr>");
		for (int i = 0; i < 26; i++)
		{
			if (i == 13)
			{
				Output.println("</tr><tr>");
			}
			Output.println("<td><div class = 'small'>" + (i+1) + "</div><br><div class = 'regular'>" + (NumToLetterCode[i] + "").toUpperCase() +"</div></td>");
		}
		Output.println("</tr></table>");
		Output.println("</table>");
		Output.println("</body>");
		Output.println("</html>");
		
		Output.close();
	}
	
	public static Word[] CopyWordArray(Word[] WordArray, int ArraySize)
	{
		Word CopiedArray[] = new Word[ArraySize];
		
		for (int i = 0; i < ArraySize; i++)
		{
			if (WordArray[i] == null)
			{
				CopiedArray[i] = null;
			}else
			{
				CopiedArray[i] = new Word("01");
				CopiedArray[i].Copy(WordArray[i]);
			}
		}
		
		return CopiedArray;
	}
	
	public static int AlphabetLetterToNum(char Letter)
	{
		Letter = Character.toUpperCase(Letter);
		for (int i = 0; i < 26; i++)
		{
			if (Alphabet[i] == Letter)
			{
				return i+1;
			}
		}
		return -1;
	}
	
	public static void AssortWords() throws IOException
	{
		PrintWriter OutputWords = new PrintWriter(System.getenv("APPDATA") + "/.CodeCracker/words.txt");
		URL Dictionary = new URL("http://app.aspell.net/create?max_size=60&spelling=AU&max_variant=0&diacritic=strip&download=wordlist&encoding=utf-8&format=inline");
	    BufferedReader in = new BufferedReader(new InputStreamReader(Dictionary.openStream()));
	    int count = 0;
        String inputLine;
        while ((inputLine = in.readLine()) != null)
        {
        	if (count > 43)
        	{
        		inputLine = inputLine.trim().toLowerCase();
        		if (inputLine.indexOf("'") == -1)
        		{
        			OutputWords.println(inputLine);
        		}
        	} 
            count++;
        }
        in.close();
		OutputWords.close();
		
		PrintWriter[] Output = new PrintWriter[10];
		BufferedReader Input = new BufferedReader(new FileReader(System.getenv("APPDATA") + "/.CodeCracker/words.txt")); 
		String Word;
		
		for (int i = 0; i < Output.length; i++)
		{
			Output[i] = new PrintWriter(System.getenv("APPDATA") + "/.CodeCracker/Words" + (i+1) + ".txt");
		}
		
        while ((Word = Input.readLine()) != null) 
        {
        	Word = Word.toLowerCase();
        	int WordLength = Word.length();
        	if (WordLength < 10 && WordLength > 0)
        	{
        		Output[WordLength - 1].println(Word);
        	}else
        	{
        		Output[9].println(Word);
        	}
        }
        
        Input.close();
        for (int i = 0; i < Output.length; i++)
		{
        	Output[i].close();
		}	
	}
	
	public static void PrintWords(int Loops, String ProcessingWord, int RecursiveLevel) throws IOException
	{
		for (int i = 0; i < 26; i++)
		{
			String NewWord = ProcessingWord.toUpperCase();
			if (LettersAvailable[i])
			{
				LettersAvailable[i] = false;
				char ReplaceNum = (RecursiveLevel + "").charAt(0);
				NewWord = NewWord.replace(ReplaceNum, Alphabet[i]);
				NewWord = NewWord.toLowerCase();
				if (Loops > 1)
				{
					Loops--;
					PrintWords(Loops, NewWord, RecursiveLevel+1);
					Loops++;
				}else
				{
					if (Word.CheckWord(NewWord))
					{
						System.out.println(NewWord);
					}
				}
				LettersAvailable[i] = true;
			}else
			{
				continue;
			}
		}
	} 
	
	@SuppressWarnings("unchecked")
	public static boolean ImportGrid() throws IOException
	{
		BufferedReader Input = new BufferedReader(new FileReader(System.getenv("APPDATA") + "/.CodeCracker/Import.txt"));
		String GridSize = Input.readLine(); //Height by Width (e.g: "19x15")
		StringTokenizer Tokenizer = new StringTokenizer(GridSize, "x", false);
		int GridHeight = Integer.parseInt(Tokenizer.nextToken());
		int GridWidth = Integer.parseInt(Tokenizer.nextToken().substring(0, 2));
		int LineNum = 0;
		String Line, GivenLetters = "";
		Word Words[] = new Word[60];
		String PuzzleNote = "";
		int Grid[][] = new int[GridHeight][GridWidth];
		
		Arrays.fill(LettersAvailable, true);
		
		while ((Line = Input.readLine()) != null) 
        {
			if (LineNum == GridHeight)
			{
				GivenLetters = Line;
				Line = Input.readLine();
				if (Line != null)
				{
					PuzzleNote = "" + Line;
				}else
				{
					PuzzleNote = null;
				}
				break;
			}
			for (int i = 0; i < GridWidth; i ++)
			{
				String Num = Line.substring(2 * i, (2 * i) + 2);
				Grid[LineNum][i] = Integer.parseInt(Num);
			}
			LineNum++;
        }
		
		for (int i = 0; i < GridHeight; i++)
		{
			for (int j = 0; j < GridWidth; j++)
			{
				if (Grid[i][j] == 0)
				{
					System.out.print("|  ");
				}else
				{
					if (Grid[i][j] < 10)
					{
						System.out.print("|0" + Grid[i][j]);
					}else
					{
						System.out.print("|" + Grid[i][j]);
					}
				}
				
			}
			System.out.println("|");
		}
		
		int WordCount = 0;
		
		for (int i = 0; i < GridHeight-1; i++)
		{
			for (int j = 0; j < GridWidth; j++)
			{
				boolean IsWord = true;
				if (i != 0)
				{
					if (Grid[i-1][j] != 0)
					{
						IsWord = false;
					}
				}
				if (Grid[i+1][j] == 0)
				{
					IsWord = false;
				}
				if (Grid[i][j] == 0)
				{
					IsWord = false;
				}
				
				if (IsWord)
				{
					int WordLen = 0;
					int[] Word = new int[15];
					while (Grid[i + WordLen][j] != 0)
					{
						Word[WordLen] = Grid[i + WordLen][j];
						
						WordLen++;
						if (i + WordLen == GridHeight)
						{
							break;
						}
					}
					String CodeWord = "";
					for (int k = 0; k < WordLen; k++)
					{
						if (Grid[i + k][j] < 10)
						{
							CodeWord = CodeWord + "0" + Grid[i + k][j];
						}else
						{
							CodeWord = CodeWord + Grid[i + k][j];
						}
					}
					Words[WordCount] = new Word(CodeWord);
					WordCount++;
				}
			}
		}
		
		for (int i = 0; i < GridHeight; i++)
		{
			for (int j = 0; j < GridWidth - 1; j++)
			{
				boolean IsWord = true;
				if (j != 0)
				{
					if (Grid[i][j-1] != 0)
					{
						IsWord = false;
					}
				}
				if (Grid[i][j+1] == 0)
				{
					IsWord = false;
				}
				if (Grid[i][j] == 0)
				{
					IsWord = false;
				}
				
				if (IsWord)
				{
					int WordLen = 0;
					int[] Word = new int[15];
					while (Grid[i][j + WordLen] != 0)
					{
						Word[WordLen] = Grid[i][j + WordLen];
						
						WordLen++;
						if (j + WordLen == GridWidth)
						{
							break;
						}
					}
					String CodeWord = "";
					for (int k = 0; k < WordLen; k++)
					{
						if (Grid[i][j + k] < 10)
						{
							CodeWord = CodeWord + "0" + Grid[i][j + k];
						}else
						{
							CodeWord = CodeWord + Grid[i][j + k];
						}
					}
					Words[WordCount] = new Word(CodeWord);
					WordCount++;
				}
			}
		}
		
		for (int i = 0; i < WordCount; i++)
		{
			System.out.println(Words[i].OriginalCode);
		}
		System.out.println("Word count is " + WordCount);
		int CommonLetters[] = new int[26];
		for (int i = 0; i < WordCount; i++)
		{
			boolean[] WordHasNumbers = Words[i].HasNumber;
			for (int j = 0; j < 26; j++)
			{
				if (WordHasNumbers[j])
				{
					CommonLetters[j]++;
				}
			}
		}
		
		for (int i = 0; i < 26; i++)
		{
			System.out.println("The letter " + (i+1) + " appeared in " + CommonLetters[i] + " out of " + WordCount + " words!");
		}
		System.out.println("Letters that appeared in 14 or more words:");
		for (int i = 0; i < 26; i++)
		{
			if (CommonLetters[i] > 13)
			System.out.print(i+1 + " ");
		}
		System.out.println("\nLetters that appeared in 3 or less words:");
		for (int i = 0; i < 26; i++)
		{
			if (CommonLetters[i] < 4)
			System.out.print(i+1 + " ");
		}
		System.out.println();
		
		
		char NumToLetterCode[] = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
		Tokenizer = new StringTokenizer(GivenLetters, " ", false);
		while(Tokenizer.hasMoreTokens())
		{
			String Token = Tokenizer.nextToken();
			if (Token.length() != 3)
			{
				System.out.println("You broke it!");
				return false;
			}
			int Num = Integer.parseInt(Token.substring(0, 2));
			char Letter = Token.charAt(2);
			NumToLetterCode[Num-1] = Letter;
			LettersAvailable[AlphabetLetterToNum(Letter)-1] = false;
		}
		
		boolean LettersAvailableBackup[] = LettersAvailable.clone();
		char NumToLetterCodeBackup[] = NumToLetterCode.clone();
		Word WordsBackup[] = new Word[60];
		WordsBackup = CopyWordArray(Words, 60);
		boolean Guessing = false;
		int GuessSolutionAttemptNum = 0;
		ArrayList<String> GuessingSolutionsList = new ArrayList<String>();
		Word GuessingSolutionWord = new Word("01");
		Word GuessingSolutionWordBackup = new Word("02");
		
		while(true)
		{
			//Say Current Letters
			System.out.println("The current letters are:");
			for (int i = 0; i < 26; i++)
			{
				if (LettersAvailable[i])
				{
					System.out.print(Alphabet[i]);
					if (i != 25)
					{
						System.out.print("  ");
					}
				}
				if (i == 25)
				{
					System.out.println();
				}
			}
			//Say Current letter/number code
			System.out.println("The current letter/number code is:");
			for (int i = 0; i < 26; i++)
			{
				if (i == 13)
				{
					System.out.println();
				}
				if (i < 9)
				{
					System.out.print("0");
				}
				System.out.print((i+1) + ": " + Character.toUpperCase(NumToLetterCode[i]) + "  ");
				if (i == 25)
				{
					System.out.println();
				}
			}
			
			//Prints out words with how many blanks they have and update each words' letters codings
			for (int i = 0; i < WordCount; i++)
			{
				boolean WordChanged = false;
				if (Words[i].Found)
				{
					continue;
				}
				for (int j = 0; j < 26; j++)
				{
					String ReplacementLetter = "" + NumToLetterCode[j];
					if (ReplacementLetter.equals(" ") || Words[i].LetterFound[j] )
					{
						continue;
					}
					Words[i].UpdateLetterDecoding(j+1, NumToLetterCode[j]);
					WordChanged = true;
				}
				if (WordChanged)
				{
					System.out.println(Words[i].ToCurrentCode().toUpperCase() + " has " + Words[i].UniqueBlanks() + " blanks");
				}
			}
			
			ArrayList<Word> WordsWithLowBlanks = new ArrayList<Word>();
			
			for (int i = 0; i < WordCount; i++)
			{
				if (Words[i].UniqueBlanks() < 4 && !Words[i].Found) 
				{
					WordsWithLowBlanks.add(Words[i]);
				}
			}
			if (WordsWithLowBlanks.size() == 0)
			{
				for (int i = 0; i < WordCount; i++)
				{
					if (Words[i].UniqueBlanks() == 4 && !Words[i].Found)
					{
						WordsWithLowBlanks.add(Words[i]);
					}
				}
			}
			Word LowSolutionsWord = null;
			int LowSolutions = 1000000;
			boolean AnyWordFoundSolutionless = false;
			for (int i = 0; i < WordsWithLowBlanks.size(); i++)
			{
				System.out.print("Searching word " + (i+1) + " out of " + WordsWithLowBlanks.size());
				int Test = WordsWithLowBlanks.get(i).FindPossibleSolutions(WordsWithLowBlanks.get(i).UniqueBlanks(), WordsWithLowBlanks.get(i).ToSearchableWord(), 1, LettersAvailable, Alphabet, 0);
				System.out.println(" - " + WordsWithLowBlanks.get(i).ToSearchableWord().toUpperCase() + " has " + Test + " solutions");
				if (Test < LowSolutions && Test != 0)
				{
					LowSolutions = Test;
					LowSolutionsWord = WordsWithLowBlanks.get(i);
				}
				if (Test == 0)
				{
					AnyWordFoundSolutionless = true;
					break;
				}
				if (LowSolutions == 1)
				{
					break;
				}
			}
			
			if (AnyWordFoundSolutionless)
			{
				if (Guessing)//If guess was wrong!
				{
					LettersAvailable = LettersAvailableBackup.clone();
					NumToLetterCode = NumToLetterCodeBackup.clone();
					Words = CopyWordArray(WordsBackup, 60);
					GuessingSolutionWord.Copy(GuessingSolutionWordBackup);
					GuessSolutionAttemptNum++;
					String CorrectSolution = GuessingSolutionsList.get(GuessSolutionAttemptNum) + "";
					boolean[] CharDone = new boolean[26];
					for (int i = 0; i < CorrectSolution.length(); i++)
					{
						int CodeNumber = GuessingSolutionWord.Code[i]; 
						char CodeLetter = CorrectSolution.toLowerCase().charAt(i);
						if(!CharDone[AlphabetLetterToNum(CodeLetter)-1] && NumToLetterCode[CodeNumber-1] == ' ')
						{
							NumToLetterCode[CodeNumber-1] = CodeLetter;
							LettersAvailable[AlphabetLetterToNum(CodeLetter)-1] = false;
							
							for (int j = 0; j < WordCount; j++)
							{
								if (!Words[j].Found)
								{
									Words[j].UpdateLetterDecoding(CodeNumber, CodeLetter);
									if (Words[j].Found)
									{
										System.out.println("Word maybe found: " + Words[j].FoundWord);
									}
								}
							}
							
							GuessingSolutionWord.UpdateLetterDecoding(CodeNumber, CodeLetter);
							
							if (GuessingSolutionWord.Found)
							{
								System.out.println(GuessingSolutionWord.FoundWord + " is being tested as a solution...");
							}
						}
						CharDone[AlphabetLetterToNum(CodeLetter)-1] = true;
					}
					continue;
				}else
				{
					System.out.println("Either this puzzle cannot be solved or the import was incorrect!");
					break;
				}
			}
			
			if (LowSolutionsWord == null) //Puzzle complete, exporting
			{
				System.out.println("Congratulations, it is now solved!");
				CreateHtmlResults(GridHeight, GridWidth, Grid, NumToLetterCode, PuzzleNote);
				
				PrintWriter Output = new PrintWriter(System.getenv("APPDATA") + "/.CodeCracker/Export.txt");
				if (PuzzleNote != null)
				{
					Output.println(PuzzleNote);
				}
				
				Output.println("----------------------------------------------------------------------------");
				for (int i = 0; i < GridHeight; i++)
				{
					for (int j = 0; j < GridWidth; j++)
					{
						if (Grid[i][j] == 0)
						{
							Output.print("| ");
						}else
						{
							char Letter = (NumToLetterCode[Grid[i][j]-1] + "").toUpperCase().charAt(0);
							Output.print("|" + Letter);
						}
					}
					Output.println("|");
				}
				Output.println("----------------------------------------------------------------------------\n");
				
				for (int i = 0; i < 26; i++)
				{
					if (i == 13)
					{
						Output.println();
					}
					if (i < 9)
					{
						Output.print("0");
					}
					Output.print((i+1) + ": " + Character.toUpperCase(NumToLetterCode[i]) + "  ");
					if (i == 25)
					{
						Output.println();
					}
				}
				Output.close();
				return true;
			}else
			{
				System.out.println("The word with the lowest solutions is " + LowSolutionsWord.ToSearchableWord().toUpperCase() + " and has " + LowSolutions + " solutions");
			}

			if (LowSolutions == 1)
			{
				String CorrectSolution = LowSolutionsWord.PossibleSolutions.get(LowSolutionsWord.PossibleSolutions.size()-1) + "";
				boolean[] CharDone = new boolean[26];
				for (int i = 0; i < CorrectSolution.length(); i++)
				{
					int CodeNumber = LowSolutionsWord.Code[i];
					char CodeLetter = CorrectSolution.toLowerCase().charAt(i);
					if(!CharDone[AlphabetLetterToNum(CodeLetter)-1] && NumToLetterCode[CodeNumber-1] == ' ')
					{
						NumToLetterCode[CodeNumber-1] = CodeLetter;
						LettersAvailable[AlphabetLetterToNum(CodeLetter)-1] = false;
						for (int j = 0; j < WordCount; j++)
						{
							if (!Words[j].Found)
							{
								Words[j].UpdateLetterDecoding(CodeNumber, CodeLetter);
								if (Words[j].Found)
								{
									System.out.println("Word found: " + Words[j].FoundWord);
								}
							}
						}
					}
					CharDone[AlphabetLetterToNum(CodeLetter)-1] = true;
				}
				continue;
			}else
			{
				if (!Guessing) 
				{
					Guessing = true;
					GuessingSolutionsList = (ArrayList<String>) LowSolutionsWord.PossibleSolutions.clone();
					GuessingSolutionWord.Copy(LowSolutionsWord); 
					GuessingSolutionWordBackup.Copy(GuessingSolutionWord);
					
					LettersAvailableBackup = LettersAvailable.clone();
					NumToLetterCodeBackup = NumToLetterCode.clone();
					WordsBackup = CopyWordArray(Words, 60);

					String CorrectSolution = GuessingSolutionsList.get(0);
					boolean[] CharDone = new boolean[26];
					for (int i = 0; i < CorrectSolution.length(); i++)
					{
						int CodeNumber = GuessingSolutionWord.Code[i];
						char CodeLetter = CorrectSolution.toLowerCase().charAt(i);
						if(!CharDone[AlphabetLetterToNum(CodeLetter)-1] && NumToLetterCode[CodeNumber-1] == ' ')
						{
							NumToLetterCode[CodeNumber-1] = CodeLetter;
							LettersAvailable[AlphabetLetterToNum(CodeLetter)-1] = false;
							
							GuessingSolutionWord.UpdateLetterDecoding(CodeNumber, CodeLetter);
							if (GuessingSolutionWord.Found)
							{
								System.out.println(GuessingSolutionWord.FoundWord + " is being tested as a solution...");
							}
							
							for (int j = 0; j < WordCount; j++)
							{
								if (!Words[j].Found)
								{
									Words[j].UpdateLetterDecoding(CodeNumber, CodeLetter);
									if (Words[j].Found && Words[j].Code != GuessingSolutionWord.Code)
									{
										System.out.println(Words[j].FoundWord + " was maybe found");
									}
								}
							}
							
							
						}
						CharDone[AlphabetLetterToNum(CodeLetter)-1] = true;
					}
				}else
				{
					//This only happens if the letters from all the guessed words (even the correct one) do not lead to a 1 solution word
					System.out.println("Bad!");
				}
			}
		}
		
		
		return false;
	}
	
	public static void main(String[] args) throws IOException, InterruptedException 
	{
		File AppDataPath = new File(System.getenv("APPDATA") + "/.CodeCracker");
		if (!AppDataPath.exists())
		{
			AppDataPath.mkdir();
			System.out.println("%APPDATA%/.CodeCracker created succesfully");
			System.out.print("Creating word files...");
			AssortWords();
			new File(System.getenv("APPDATA") + "/.CodeCracker/words.txt").delete();
			new File(System.getenv("APPDATA") + "/.CodeCracker/Words1.txt").delete();
			new File(System.getenv("APPDATA") + "/.CodeCracker/Words2.txt").delete();
			System.out.println(" DONE!");
			PrintWriter ImportFile = new PrintWriter(System.getenv("APPDATA") + "/.CodeCracker/Import.txt");
			PrintWriter ExportFile = new PrintWriter(System.getenv("APPDATA") + "/.CodeCracker/Export.txt");
			ImportFile.println("This is where your import data goes!");
			ExportFile.println("This is where your export data will be!");
			ImportFile.close();
			ExportFile.close();
			Files.copy(new File("./CodeCracker.jar").toPath(), new File(System.getenv("APPDATA") + "/.CodeCracker/CodeCracker.jar").toPath(), StandardCopyOption.REPLACE_EXISTING);
			JarFile jarfile = new JarFile(new File(System.getenv("APPDATA") + "/.CodeCracker/CodeCracker.jar")); 
		    Enumeration<JarEntry> enu= jarfile.entries();
		    while(enu.hasMoreElements())
		    {
		        String destdir = System.getenv("APPDATA") + "/.CodeCracker/"; 
		        JarEntry je = enu.nextElement();

		        File fl = new File(destdir, "ImportMaker.html");
		        if (je.getName().equals("mainPackage/ImportMaker.html"))
		        {
			        InputStream is = jarfile.getInputStream(je);
			        FileOutputStream fo = new FileOutputStream(fl);
			        while(is.available()>0)
			        {
			            fo.write(is.read());
			        }
			        fo.close();
			        is.close();
		        }
		    }
		    jarfile.close();
		    new File(System.getenv("APPDATA") + "/.CodeCracker/CodeCracker.jar").delete();
		}
		
		Arrays.fill(LettersAvailable, true);
		
		while (true)
		{
			System.out.println("-------------------------------------------------");
			System.out.println("1. Remove letters");
			System.out.println("2. Re-add letters");
			System.out.println("3. Show current letters");
			System.out.println("4. Reset letters");
			System.out.println("5. Find word");
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println("6. Open Import.txt creator");
			System.out.println("7. Open Import.txt");
			System.out.println("8. Import grid");
			
			
			
			String Input = keyboard.nextLine();
			System.out.println("");
			
			switch (Input)
			{
				case "1":
					System.out.println("Enter letters to be removed:");
					String remove = keyboard.nextLine();
					boolean LetterRemoved = false;
					remove = remove.toUpperCase();
					remove = remove.trim();
					
					System.out.print("Removing letters: ");
					
					for (int i = 0; i < remove.length(); i ++)
					{
						char Letter = remove.charAt(i);
						if ("ABCDEFGHIJKLMNOPQRSTUVWXYZ".contains("" + remove.charAt(i))) //If the current character in the string is a letter
						{
							for (int j = 0; j < 26; j++)
							{
								if (Letter == Alphabet[j] && LettersAvailable[j])
								{
									LettersAvailable[j] = false;
									LetterRemoved = true;
									System.out.print(Alphabet[j] + "  ");
									break;
								}
							}
						}
					}
					if (!LetterRemoved)
					{
						System.out.println("\nNO LETTERS REMOVED!!");
					}else
					{
						System.out.println();
					}
					break;
					
				case "2": //THIS DOESNT WORK (though, it really doesn't matter)
					System.out.println("Enter letters to be re-added:");
					String readd = keyboard.nextLine();
					boolean LetterReadded = false;
					remove = readd.toUpperCase();
					remove = readd.trim();
					
					System.out.print("Re-adding letters: ");
					
					for (int i = 0; i < readd.length(); i ++)
					{
						char Letter = readd.charAt(i);
						if ("ABCDEFGHIJKLMNOPQRSTUVWXYZ".contains("" + readd.charAt(i))) //If the current character in the string is a letter
						{
							for (int j = 0; j < 26; j++)
							{
								if (Letter == Alphabet[j] && !LettersAvailable[j])
								{
									LettersAvailable[j] = true;
									LetterReadded = true;
									System.out.print(Alphabet[j] + "  ");
									break;
								}
							}
						}
					}
					if (!LetterReadded)
					{
						System.out.println("\nNO LETTERS RE-ADDED!!");
					}else
					{
						System.out.println();
					}
					break;
					
				case "3":
					System.out.println("The current letters are:");
					
					for (int i = 0; i < 26; i++)
					{
						if (LettersAvailable[i])
						{
							System.out.print(Alphabet[i]);
							if (i != 25)
							{
								System.out.print("  ");
							}
						}
						if (i == 25)
						{
							System.out.println();
						}
					}
					
					break;
					
				case "4":
					for (int i = 0; i < 26; i++)
					{
						LettersAvailable[i] = true;
					}
					System.out.println("All letters are now avaiable");
					break;
					
				case "5":
					boolean WrongCode = false;
					System.out.println("Enter word (use numbers 1-4 for blanks)");
					String code = keyboard.nextLine();
					code = code.toUpperCase();
					for (int i = 0; i < code.length(); i++)
					{
						if (!("ABCDEFGHIJKLMNOPQRSTUVWXYZ1234".contains("" + code.charAt(i)))) 
						{
							WrongCode = true;
							break;
						}
					}
					if (WrongCode)
					{
						System.out.println("The code was wrong, silly");
						break;
					}
					
					
					if (code.contains("1") || code.contains("2") || code.contains("3") || code.contains("4"))
					{
						int Blanks = 0;
						while(true)
						{
							if (code.contains("1") && code.contains("2") && code.contains("3") && code.contains("4"))//has all 4 numbers
							{
								Blanks = 4;
								break;
							}
							
							if (code.contains("1") && code.contains("2") && code.contains("3") && !code.contains("4"))//has 3 numbers (missing 4)
							{
								Blanks = 3;
								break;
							}
							if (code.contains("1") && code.contains("2") && !code.contains("3") && code.contains("4"))//has 3 numbers (missing 3)
							{
								code = code.replace('4', '3');
								Blanks = 3;
								break;
							}
							if (code.contains("1") && !code.contains("2") && code.contains("3") && code.contains("4"))//has 3 numbers (missing 2)
							{
								code = code.replace('4', '2');
								Blanks = 3;
								break;
							}
							if (!code.contains("1") && code.contains("2") && code.contains("3") && code.contains("4"))//has 3 numbers (missing 1)
							{
								code = code.replace('4', '1');
								Blanks = 3;
								break;
							}
							
							if (code.contains("1") && code.contains("2") && !code.contains("3") && !code.contains("4")) //has 2 numbers (has 1 and 2)
							{
								Blanks = 2;
								break;
							}
							if (code.contains("1") && !code.contains("2") && code.contains("3") && !code.contains("4")) //has 2 numbers (has 1 and 3)
							{
								code = code.replace('3', '2');
								Blanks = 2;
								break;
							}
							if (code.contains("1") && !code.contains("2") && !code.contains("3") && code.contains("4")) //has 2 numbers (has 1 and 4)
							{
								code = code.replace('4', '2');
								Blanks = 2;
								break;
							}
							if (!code.contains("1") && code.contains("2") && code.contains("3") && !code.contains("4")) //has 2 numbers (has 2 and 3)
							{
								code = code.replace('3', '1');
								Blanks = 2;
								break;
							}
							if (!code.contains("1") && code.contains("2") && !code.contains("3") && code.contains("4")) //has 2 numbers (has 2 and 4)
							{
								code = code.replace('4', '1');
								Blanks = 2;
								break;
							}
							if (!code.contains("1") && !code.contains("2") && code.contains("3") && code.contains("4")) //has 2 numbers (has 3 and 4)
							{
								code = code.replace('4', '1');
								code = code.replace('3', '2');
								Blanks = 2;
								break;
							}
							
							if (code.contains("1") && !code.contains("2") && !code.contains("3") && !code.contains("4")) //has 1 numbers (has 1)
							{
								Blanks = 1;
								break;
							}
							if (!code.contains("1") && code.contains("2") && !code.contains("3") && !code.contains("4")) //has 1 numbers (has 2)
							{
								code = code.replace('2', '1');
								Blanks = 1;
								break;
							}
							if (!code.contains("1") && !code.contains("2") && code.contains("3") && !code.contains("4")) //has 1 numbers (has 3)
							{
								code = code.replace('3', '1');
								Blanks = 1;
								break;
							}
							if (!code.contains("1") && !code.contains("2") && !code.contains("3") && code.contains("4")) //has 1 numbers (has 4)
							{
								code = code.replace('4', '1');
								Blanks = 1;
								break;
							}
							System.out.println("Something is wrong!! There are " + Blanks + " blanks");
							break;
						}
						System.out.println("your word is " + code.toLowerCase() + " and you have " + Blanks + " blanks");
						System.out.println("Possible words: ");
						if (Blanks < 1 || Blanks > 4)
						{
							System.out.println("You broke it");
							break;
						}
						double StartTime = System.nanoTime();
						PrintWords(Blanks, code, 1);
						double TimeTaken = ((double)System.nanoTime() - StartTime)/ 1000000000;
						System.out.println("This search took " + TimeTaken + " seconds");
					}else
					{
						if (Word.CheckWord(code.toLowerCase()))
						{
							System.out.println(code.toLowerCase() + " is in the dictionary!");
						}else
						{
							System.out.println(code.toLowerCase() + " is NOT in the dictionary!");
						}
						break;
					}
					break;
					
				case "6":
					File OpenImportMaker = new File(System.getenv("APPDATA") + "/.CodeCracker/ImportMaker.html");
					Desktop.getDesktop().browse(OpenImportMaker.toURI());
					break;
					
				case "7":
					File OpenImport = new File(System.getenv("APPDATA") + "/.CodeCracker/Import.txt");
					java.awt.Desktop.getDesktop().edit(OpenImport);
					break;
				
				case "8":
					double StartTime = System.nanoTime();
					boolean Solved = ImportGrid();
					double TimeTaken = ((double)System.nanoTime() - StartTime)/ 1000000000;
					if (Solved)
					{
						System.out.println("The puzzle took " + TimeTaken + " seconds to solve!\n\n");
					}else
					{
						System.out.println("It only took a mere " + TimeTaken + " seconds for everything to go wrong...\n\n");
						break;
					}
					File htmlFile = new File(System.getenv("APPDATA") + "/.CodeCracker/Results.html");
					Desktop.getDesktop().browse(htmlFile.toURI());
					System.exit(1);
					break;
					
				case "exit":
					System.out.println("Closing....");
					keyboard.close(); 
					System.exit(0);
					
				default:
					break;
			}
		}
		
	}
}