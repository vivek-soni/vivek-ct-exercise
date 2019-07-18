package handson.impl;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletionStage;

import com.neovisionaries.i18n.CountryCode;

import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.commands.Command;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerDraft;
import io.sphere.sdk.customers.CustomerDraftDsl;
import io.sphere.sdk.customers.CustomerName;
import io.sphere.sdk.customers.CustomerSignInResult;
import io.sphere.sdk.customers.CustomerToken;
import io.sphere.sdk.customers.commands.CustomerCreateCommand;
import io.sphere.sdk.customers.commands.CustomerCreateEmailTokenCommand;
import io.sphere.sdk.customers.commands.CustomerVerifyEmailCommand;
import io.sphere.sdk.models.Address;

/**
 * This class provides operations to work with {@link Customer}s.
 */
public class CustomerService extends AbstractService {

	public CustomerService(final SphereClient client) {
		super(client);
	}

	/**
	 * Creates a new customer {@link Customer} with the given parameters.
	 *
	 * @param email    the customers email
	 * @param password the customers password
	 * @return the customer creation completion stage
	 */
	public CompletionStage<CustomerSignInResult> createCustomer(final String email, final String password) {
		final CustomerName name = CustomerName.ofFirstAndLastName("Prateek", "Gupta");
		List<Address> address = Arrays.asList(Address.of(CountryCode.US), Address.of(CountryCode.US));

		CustomerDraft draft = CustomerDraftDsl.of(name, email, password).withAddresses(address);
		final CustomerCreateCommand sphereRequest = CustomerCreateCommand.of(draft);
		final CompletionStage<CustomerSignInResult> customer = client.execute(sphereRequest);
		return customer;
	}

	/**
	 * Creates an email verification token for the given customer. This is then used
	 * to create a password reset link.
	 *
	 * @param customer            the customer
	 * @param timeToLiveInMinutes the time to live (in minutes) for the token
	 * @return the customer token creation completion stage
	 */
	public CompletionStage<CustomerToken> createEmailVerificationToken(final Customer customer,
			final Integer timeToLiveInMinutes) {

		Command<CustomerToken> createTokenCommand = CustomerCreateEmailTokenCommand.ofCustomerId(customer.getId(),
				timeToLiveInMinutes);

		final CompletionStage<CustomerToken> customerToken = client.execute(createTokenCommand);

		return customerToken;
	}

	/**
	 * Verifies the customer token.
	 *
	 * @param customerToken the customer token
	 * @return the email verification completion stage
	 */
	public CompletionStage<Customer> verifyEmail(final CustomerToken customerToken) {

		final Command<Customer> verifyEmailCommand = CustomerVerifyEmailCommand.ofTokenValue(customerToken.getValue());

		final CompletionStage<Customer> newCustomer = client.execute(verifyEmailCommand);

		return newCustomer;
	}
}
