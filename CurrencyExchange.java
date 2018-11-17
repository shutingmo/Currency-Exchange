//Name: Cynthia Mo
//UFL ID: 04938124
//Section: 2296
//Project Number: 3
//Brief description of file contents: Currency Exchange

import java.util.Scanner; 
import java.lang.Math; 

public class CurrencyExchange 
{
	private static double balance = 0;
	 
	public static double getBalance() 
	{
		return balance;
	}
	 
	private static boolean updateBalance(double newBalance) 
	{
		balance = Math.round(newBalance * 100) / 100.0;
		if (balance >= 0) 
		{
			return true;
		}
		else
		{
			return false;
		}	
	}
	
	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);

		System.out.println("Welcome to the Currency Exchange 2.0\n");
		System.out.println("Current rates are as follows:\n");
		printConversionTable();
		
		while (true)
		{
			int mainMenuChoice = mainMenuOptionSelector(input);

			if ((mainMenuChoice < 1) || (mainMenuChoice > 4))
			{
				while ((mainMenuChoice < 1) || (mainMenuChoice > 4))
				{
					System.out.println("Input failed validation. Please try again.");
					System.out.println("Please select an option from the list below:");
					System.out.println("1. Check the balance of your account");
					System.out.println("2. Make a deposit");
					System.out.println("3. Withdraw an amount in a specific currency");
					System.out.println("4. End your session (and withdraw all remaining currency in U.S. Dollars)");
					mainMenuChoice = input.nextInt();
				}
			}
		
			else if (mainMenuChoice == 1) //balance
			{
				System.out.println("Your current balance is: " + getBalance());
			}
			
			else if (mainMenuChoice == 2) //deposit
			{
				int currencyNumber = currencyMenuOptionSelector(input);
				System.out.println("Please enter the deposit amount: ");
				double depositAmount = input.nextDouble();
				deposit(depositAmount, currencyNumber);
				
				//boolean depositTrue = true; 
				//logTransaction(depositAmount, currencyNumber, depositTrue);
				
				/*
				if (currencyNumber != 1)
				{
					double convertedAmount = convertCurrency (depositAmount, currencyNumber, true);
					updateBalance (convertedAmount);
				} */
			}
			
			else if (mainMenuChoice == 3) //Withdrawal
			{
				int currencyNumber = currencyMenuOptionSelector(input);
				
				System.out.println("Please enter the withdrawl amount :");
				double withdrawlAmount = input.nextDouble();
				withdraw (withdrawlAmount, currencyNumber);
				//logTransaction(withdrawlAmount, currencyNumber, false);
				//double newBalance = getBalance () - withdrawlAmount; 
				//updateBalance (newBalance);
			}
			
			else //if (mainMenuChoice == 4) // end session and withdraw all remaining currency in US dollars
			{				
				//withdraw(getBalance(), 1);
				//logTransaction(getBalance(), 1, false);
				
				System.out.println("\nGoodbye");
				
				break;
			}
		}
	}
	
	public static void printConversionTable()
	{
		System.out.println("1 -  U.S. Dollar - 1.00");
		System.out.println("2 - Euro - 0.89");
		System.out.println("3 - British Pound - 0.78");
		System.out.println("4 - Indian Rupee - 66.53");
		System.out.println("5 - Australian Dollar - 1.31");
		System.out.println("6 - Canadian Dollar - 1.31");
		System.out.println("7 - Singapore Dollar - 1.37");
		System.out.println("8 - Swiss Franc - 0.97");
		System.out.println("9 - Malaysian Ringgit - 4.12");
		System.out.println("10 - Japanese Yen - 101.64"); 
		System.out.println("11 - Chinese Yuan Renminbi - 6.67\n");
	}
	
	public static int mainMenuOptionSelector(Scanner input)
	{
		System.out.println("\nPlease select an option from the list below:");
		System.out.println("1. Check the balance of your account");
		System.out.println("2. Make a deposit");
		System.out.println("3. Withdraw an amount in a specific currency");
		System.out.println("4. End your session (and withdraw all remaining currency in U.S. Dollars)");
		
		int menuChoice = input.nextInt(); 
		if ((menuChoice < 1) || (menuChoice > 4))
		{
			while ((menuChoice < 1) || (menuChoice > 4))
				{
					System.out.println("Invalid choice. Please choose again");
					System.out.println("Please select an option from the list below:");
					System.out.println("1. Check the balance of your account");
					System.out.println("2. Make a deposit");
					System.out.println("3. Withdraw an amount in a specific currency");
					System.out.println("4. End your session (and withdraw all remaining currency in U.S. Dollars)");
					menuChoice = input.nextInt();
				}
		}
		return menuChoice; 
	}
	
	public static int currencyMenuOptionSelector(Scanner input)
	{
		System.out.println("Please Select the currency type: ");
		System.out.println("1. U.S. Dollars");
		System.out.println("2. Euros");
		System.out.println("3. British Pounds");
		System.out.println("4. Indian Rupees");
		System.out.println("5. Australian Dollars");
		System.out.println("6. Canadian Dollars");
		System.out.println("7. Singapore Dollars");
		System.out.println("8. Swiss Francs");
		System.out.println("9. Malaysian Ringgits");
		System.out.println("10. Japanese Yen");
		System.out.println("11. Chinese Yuan Renminbi");
		
		int currencyChoice = input.nextInt();

		if ((currencyChoice < 1) || (currencyChoice > 11))
			while ((currencyChoice < 1) || (currencyChoice > 11))
			{
				System.out.println("Invalid choice. Please choose again");
				System.out.println("Please Select the currency type: ");
				System.out.println("1. U.S. Dollars");
				System.out.println("2. Euros");
				System.out.println("3. British Pounds");
				System.out.println("4. Indian Rupees");
				System.out.println("5. Australian Dollars");
				System.out.println("6. Canadian Dollars");
				System.out.println("7. Singapore Dollars");
				System.out.println("8. Swiss Francs");
				System.out.println("9. Malaysian Ringgits");
				System.out.println("10. Japanese Yen");
				System.out.println("11. Chinese Yuan Renminbi");
				
				currencyChoice = input.nextInt();
			}
		return currencyChoice; 
	}
	
	public static double convertCurrency(double amount, int currencyType, boolean isConvertToUSD)
	{
		double exchangeRate = 0;
		switch (currencyType)
		{
		case 1: exchangeRate = 1.00; break;
		case 2: exchangeRate = 0.89; break;
		case 3: exchangeRate = 0.78; break;
		case 4: exchangeRate = 66.53; break;
		case 5: exchangeRate = 1.31; break;
		case 6: exchangeRate = 1.31; break;
		case 7: exchangeRate = 1.37; break;
		case 8: exchangeRate = 0.97; break;
		case 9: exchangeRate = 4.12; break;
		case 10: exchangeRate = 101.64; break;
		case 11: exchangeRate = 6.67; break;
		}
				
		double convertedAmount = 0;
		if (isConvertToUSD)
		{
			//foreign currency to USD
			convertedAmount = (amount / exchangeRate); 
		} 
		
		else
		{
			//USD to the foreign currency
			convertedAmount = (amount * exchangeRate);
		}
		return convertedAmount; 
	}
	
	public static boolean deposit(double amount, int currencyType)
	{	
		if ((currencyType < 1) || (currencyType > 11))
			return false; 
		
		if (amount > 0)
		{
			if (currencyType != 1)
			{
				double convertedAmount = convertCurrency (amount, currencyType, true);
				//System.out.println(" ------ " +convertedAmount);
				balance = getBalance();
				System.out.println(balance + "!");
				double updatedBalance = balance + convertedAmount; 
				System.out.println(updatedBalance + "!!");
				
				updateBalance (updatedBalance);
				return true;
			}
			
			else if ((currencyType >= 1) && (currencyType <= 11))
			{
				double convertedAmount = convertCurrency (amount, currencyType, false);
				//updateBalance(convertedAmount);
				balance = getBalance();
				System.out.println(balance + "@");
				double updatedBalance = balance + convertedAmount; 
				System.out.println(updatedBalance + "@@");
				
				updateBalance (updatedBalance);
				return true;
			}
			
		}
		
		return false;
		
	}
	
	public static boolean withdraw(double amount, int currencyType)
	{
		double totalWithdrawl = 0; 
		double balance = getBalance(); 
		double newBalance = 0; 
		
		if (amount > 0)
		{
			if (currencyType != 1) //foreign currency
			{
				totalWithdrawl = ((convertCurrency(amount, currencyType, true)) * 1.005); 
				System.out.println("total withdrawn including fees is " + totalWithdrawl);
							
				System.out.println("balance is " + balance);
				
				if (totalWithdrawl > balance) 
				{
					System.out.println("Error: Insufficient funds.");
					return false; 
				} 
				
				else //totalWithdrawl <= balance
				{
					newBalance = balance - totalWithdrawl; 
					System.out.println(newBalance);
					updateBalance(newBalance);

				}
				
				 
			}
			
			else  //USD
			{
				System.out.println("The amount is " + amount);

				balance = getBalance();
				System.out.println("The balance is " + balance);
				
				if (amount > balance)
				{
					System.out.println("Error: Insufficient funds.");	
					return false;
				} 
				
				else if (amount <= balance)
				{
					newBalance = balance - amount; 
					updateBalance (newBalance);
					
					System.out.println("The updated balance is " + newBalance);					
				}
			}
			
			return true; 
		}		
		return false;
	}
}
