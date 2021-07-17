Feature: As a User I want to add Products to Cart


  Scenario: Adding Product "sa32" to Cart
    Given User is in Main Category Page
    When User goes to Category Page "Vintage&Nature"
    And User goes to Product Page "sa32" and clicks Add to Cart
    Then Cart Page with Product "sa32" is displayed

  Scenario Outline: Adding diffrent products <symbol> to Cart
    Given User is in Main Category Page
    When User goes to Category Page <category>
    And User goes to Product Page <symbol> and clicks Add to Cart
    Then Cart Page with Product <symbol> is displayed
    Examples:
      | category          | symbol    | symbol     |
      | "Pastellove"      | "PL47"    | "PL47"     |
      | "Mystic Moment"   | "mm04"    | "mm04"     |



  Scenario: Adding two products to Cart
    Given User is in Main Category Page
    When User goes to Category Page "Vintage&Nature"
    And User goes to Product Page "sa32" and clicks Add to Cart
    And User goes to Category Page "Pastellove"
    And User goes to Product Page "PL47" and clicks Add to Cart
    Then Cart Page with Product "sa32" and Product "PL47" is displayed

   Scenario Outline: Adding two products to Cart
     Given User is in Main Category Page
     When User goes to Category Page <category>
     And User goes to Product Page <symbol> and clicks Add to Cart
     And User goes to Category Page <category2>
     And User goes to Product Page <symbol2> and clicks Add to Cart
     Then Cart Page with Product <symbol> and Product <symbol2> is displayed
     Examples:
       | category          | symbol    | category2         | symbol   | symbol  |  symbol2    |
       | "Pastellove"      | "PL47"    | "Vintage&Nature"  | "sa32"   | "PL47"  | "sa32"      |
       | "Mystic Moment"   | "mm04"    | "Pastellove"      | "PL47"   | "mm04"  | "PL47"      |