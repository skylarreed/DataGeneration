package com.sr.datagen.utilities;
public class MerchantNameGenerator {

    private static String[] agriculturalServices = {"Tractor Supply Co.", "Boones Creek Animal Hospital",
            "SR Landscaping", "B & B Farm Supply", "Vet Hospital"};

    private static String[] contractServices = {"A & A Contracting", "SR Plumbing" , "SR Electric", "SR Heating & Cooling",
            "SR Construction", "Air Movers", "B & B Contracting", "SR Contracting"};

    private static String[] transportationService = {"Uber", "Lyft", "SR Transportation", "B & B Transportation",
            "SR Taxi", "B & B Taxi", "Turo", "Public Transportation", "Hertz", "Enterprise", "Avis", "Budget"};

    private static String[] utilityServices = {"SR Electric", "SR Gas", "SR Water", "SR Sewer", "SR Cable", "SR Internet",
            "SR Phone", "SR Trash", "SR Recycling", "SR Heating & Cooling", "SR Plumbing"};

    private static String[] retailServices = {"Walmart", "Target", "Kroger", "CVS", "Walgreens", "Dollar General",
            "Dollar Tree", "Family Dollar", "Fresh Market", "Trader Joes", "Whole Foods", "Publix", "Harris Teeter"};

    private static String[] clothingStores = {"American Eagle", "Abercrombie & Fitch", "Hollister", "Old Navy", "Gap",
            "Banana Republic", "J. Crew", "Express", "Nordstrom", "Macy's", "Sears", "JC Penney", "Dillards", "Burlington",
            "TJ Maxx", "Marshalls", "Ross", "Saks Fifth Avenue", "Neiman Marcus", "Bloomingdales", "Nordstrom Rack",
            "Gymshark", "Under Armour", "Nike", "Adidas", "Puma", "Reebok", "Lululemon", "Victoria's Secret", "Aerie",};

    private static String[] miscStores = {"Netflix", "Amazon", "Apple", "Google", "Microsoft", "Facebook", "Twitter",
            "Instagram", "Snapchat", "TikTok", "Spotify", "Hulu", "Disney+", "HBO Max", "CBS All Access", "Amazon Prime",
            "Apple Music", "Google Play", "Microsoft Office"};

    private static String[] businessService = {"Google Ads", "SR Advertising", "Equifax", "Experian", "TransUnion",
            "B & B Advertising", "SR Marketing", "SR Detective Agency", "B & B Detective Agency", "Big Man Accounting"};

    private static String[] professionalServices = {"Mayo Clinic", "Ballad Health Alliance", "S.R. Medical Group",
            "B & B Medical Group", "SR Hospital", "B & B Hospital", "SR Pharmacy", "B & B Pharmacy", "SR Dentist",
            "Sunny Side Day Care"};

    private static String[] governmentServices = {"IRS Tax Payment", "IRS Tax Refund", "Social Security", "Medicare",
            "Medicaid", "Unemployment", "State Tax Payment", "State Tax Refund", "Federal Tax Payment",
            "City Tax Payment", "City Tax Refund", "County Tax Payment", "County Tax Refund", "School Tax Payment",
             "Property Tax Payment"};

    public static String getMerchantName(int mcc){
        String category = getMerchantCategoryCode(mcc);
        return switch (category) {
            case "Agricultural Services" -> agriculturalServices[(int) (Math.random() * agriculturalServices.length)];
            case "Contract Services" -> contractServices[(int) (Math.random() * contractServices.length)];
            case "Transportation Services" ->
                    transportationService[(int) (Math.random() * transportationService.length)];
            case "Utility Services" -> utilityServices[(int) (Math.random() * utilityServices.length)];
            case "Retail Services" -> retailServices[(int) (Math.random() * retailServices.length)];
            case "Clothing Stores" -> clothingStores[(int) (Math.random() * clothingStores.length)];
            case "Miscellaneous Stores" -> miscStores[(int) (Math.random() * miscStores.length)];
            case "Business Services" -> businessService[(int) (Math.random() * businessService.length)];
            case "Professional Services" -> professionalServices[(int) (Math.random() * professionalServices.length)];
            case "Government Services" -> governmentServices[(int) (Math.random() * governmentServices.length)];
            default -> "Unknown";
        };
    }
    public static String getMerchantCategoryCode(int mcc) {
        String category;
        if(mcc > 0 && mcc < 1500){
            category = "Agricultural Services";

        }else if(mcc > 1499 && mcc < 3000){
            category = "Contracted Services";
        }else if(mcc > 3990 && mcc < 4800){
            category = "Transportation Services";
        }else if(mcc > 4799 && mcc < 5000) {
            category = "Utility Services";
        }else if(mcc > 4999 && mcc < 5600) {
            category = "Retail Services";
        }else if(mcc > 5599 && mcc < 5700) {
            category = "Clothing Stores";
        } else if (mcc > 5699 && mcc < 7300) {
            category = "Miscellaneous Stores";
        } else if (mcc > 7299 && mcc < 8000) {
            category = "Business Services";
        }
        else if (mcc > 7999 && mcc < 9000) {
            category = "Professional Services";
        }else if (mcc > 8999 && mcc <= 9999) {
            category = "Government Services";
        }else{
            category = "Unknown";
        }
        return category;
    }

}

/**
 * MCCs 0001–1499: Agricultural Services
 * MCCs 1500–2999: Contracted Services
 * MCCs 4000–4799: Transportation Services
 * MCCs 4800–4999: Utility Services
 * MCCs 5000–5599: Retail Outlet Services
 * MCCs 5600–5699: Clothing Stores
 * MCCs 5700–7299: Miscellaneous Stores
 * MCCs 7300–7999: Business Services
 * MCCs 8000–8999: Professional Services and Membership Organizations
 * MCCs 9000–9999: Government Services
 */