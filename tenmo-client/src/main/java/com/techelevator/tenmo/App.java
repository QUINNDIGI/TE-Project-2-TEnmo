package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;
import com.techelevator.tenmo.services.TransferService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);

    private AuthenticatedUser currentUser;

    private AccountService accountService = new AccountService(API_BASE_URL);
    private TransferService transferService = new TransferService(API_BASE_URL);


    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }
    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");

            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

	private void viewCurrentBalance() {
        BigDecimal balance = accountService.getBalance(currentUser);
		System.out.println ("Your current balance is: $" + balance);
	}

	private void viewTransferHistory() {

        List<ApiTransfer> listApiTransfers = transferService.getTransferHistory(currentUser);
        int transferId=  consoleService.promptForInt("Please enter transfer ID to view details (0 to cancel: ");
        if (transferId != 0) {

            for (ApiTransfer apiTransfer : listApiTransfers) {
                if (apiTransfer.getTransferId().intValue() == transferId) {
                    transferService.printTransferDetails(apiTransfer);
                }
            }
        }
	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub
		
	}

	private void sendBucks() {
		List<User> userList = new ArrayList<>();

        transferService.listUsers(currentUser);
        int userId = consoleService.promptForInt("Enter ID of user you are sending to (0 to cancel):");
        if (currentUser.getUser().getId().intValue() == userId)
        {
            System.out.println ("You cannot send money to yourself.");
            return;
        }

        BigDecimal balance = accountService.getBalance(currentUser);

        if (userId != 0 ) {

            BigDecimal amount = consoleService.promptForBigDecimal("Enter amount: $ ");
            int balanceCompare = balance.compareTo(amount);
            BigDecimal zero = new BigDecimal ("0.00");
            int compareToZero = amount.compareTo(zero);
            if (balanceCompare >=0  && compareToZero >= 0) {
                transferService.transferToUser(currentUser, userId, amount);
            }
            else
            {
                System.out.println("The amount transferred must be less than or equal to the balance in your account.");
                System.out.println("The amount transferred must be less than or equal to 0.");
            }

        }
	}

	private void requestBucks() {
		// TODO Auto-generated method stub
		
	}

}
