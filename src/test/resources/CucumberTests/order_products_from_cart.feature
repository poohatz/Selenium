Feature: As a User I want to order products from cart



  Scenario: Order by Paying Later - Happy Path
    Given User is in Cart_Page with One Product "sa32" from "Vintage&Nature"
    When User changes quantity to 23, checks cash on delivery option, standard mode option and clicks Save and Order button
    And User enters correct data to Address Form and clicks Continue
    | name     | Maria Biedronka    |
    | address  | Kowalewskiego 3/4  |
    | city     | Sopot              |
    | code     | 80-321             |
    | email    | maria1@o2.pl       |
    | tel      | 600 151 804        |
    | comments | prosze o formularz |
    | nip      | 7393300440         |
    And User checks Terms Confirmation option, RODO accept option and clicks Finalize and Confirm Order
    Then Message informing about making order is displayed

  Scenario Outline: Order by Paying Later Others - Happy Path
    Given User is in Cart_Page with One Product <symbol> from <category>
    When User changes quantity to <quantity>, checks cash on delivery option, standard mode option and clicks Save and Order button
    And User enters correct data to Address Form and clicks Continue
      | name     | <name>     |
      | address  | <address>  |
      | city     | <city>     |
      | code     | <code>     |
      | email    | <email>    |
      | tel      | <tel>      |
      | comments | <comments> |
      | nip      | <nip>      |
    And User checks Terms Confirmation option, RODO accept option and clicks Finalize and Confirm Order
    Then Message informing about making order is displayed
    Examples:
    |symbol  |category        |quantity|name            |address          |city       |code    |email        |tel        |comments|nip        |
    |"sa32"  |"Vintage&Nature"|123     |Maja Kropka     |Derc 23          |Jeziorany  |12-123  |maja@wp.pl   |123123213  |        |321321321  |



  Scenario: Order by Paying Later, Delivery Type unchecked - Negative Path
    Given User is in Cart_Page with One Product "sa32" from "Vintage&Nature"
    When User changes quantity to 41, checks standard mode option and clicks Save and Order button
    Then Alert informing User must choice Delivery Option

  Scenario Outline: Order by Paying Later, Delivery Type unchecked - Negative Path
    Given User is in Cart_Page with One Product <symbol> from <category>
    When User changes quantity to <quantity>, checks standard mode option and clicks Save and Order button
    Then Alert informing User must choice Delivery Option
    Examples:
      |symbol  |category           |quantity|
      |"mm04"  |"Mystic Moment"    |123     |
      |"PL47"  |"Pastellove"       |57      |



  Scenario: Order by Paying Later, Invalid email - Negative Path
    Given User is in Cart_Page with One Product "sa32" from "Vintage&Nature"
    When User changes quantity to 23, checks cash on delivery option, standard mode option and clicks Save and Order button
    And User enters data with invalid email to Address Form and clicks Continue
      | name     | Maria Biedronka    |
      | address  | Kowalewskiego 3/4  |
      | city     | Sopot              |
      | code     | 80-321             |
      | email    | maria1o2.pl        |
      | tel      | 600 151 804        |
      | comments | prosze o formularz |
      | nip      | 7393300440         |
    Then Alert informing User must enter correct email

  Scenario Outline: Order by Paying Later, Invalid email - Negative Path
    Given User is in Cart_Page with One Product <symbol> from <category>
    When User changes quantity to <quantity>, checks cash on delivery option, standard mode option and clicks Save and Order button
    And User enters data with invalid email to Address Form and clicks Continue
      | name     | <name>     |
      | address  | <address>  |
      | city     | <city>     |
      | code     | <code>     |
      | email    | <email>    |
      | tel      | <tel>      |
      | comments | <comments> |
      | nip      | <nip>      |
    Then Alert informing User must enter correct email
    Examples:
      |symbol  |category        |quantity|name            |address          |city       |code    |email        |tel        |comments|nip        |
      |"sa32"  |"Vintage&Nature"|123     |Maja Kropka     |Derc 23          |Jeziorany  |12-123  |majawp.pl    |123123213  |        |321321321  |
      |"mm04"  |"Mystic Moment" |23      |Maja Kowalska   |Leyka 12/3       |Olsztyn    |10-234  |msdfsdfsd    |123100003  |        |320000021  |