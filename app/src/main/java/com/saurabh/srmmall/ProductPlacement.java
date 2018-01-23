package com.saurabh.srmmall;


import android.content.Context;
import android.widget.ImageView;

import java.util.ArrayList;

public class ProductPlacement {
    ImageView imageView;
    Context context;
    ArrayList<Integer> ImageList;

    public ProductPlacement(ImageView imageView, String product , Context context) {
        this.imageView = imageView;
        this.context = context;
        SetImage(product.trim());

    }

    private void SetImage(String product) {
        ImageList = new ArrayList<>();
        if (getProduct(product)==null){
            imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.camera));
            return;
        }
        ProductActivity.isImageSaved = true;
        switch (getProduct(product)) {
            case "Haldiram Bhaujia Sev":
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.bhujia_sev));
                imageView.setTag(R.drawable.bhujia_sev);
                break;
            case "Blue Lays":
                ImageList.add(R.drawable.blue_lays);
                ImageList.add(R.drawable.red_lays);
                ImageList.add(R.drawable.lays_green);
                ImageList.add(R.drawable.lays_green_ii);
                ImageList.add(R.drawable.lays_orangs);
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.blue_lays));
                imageView.setTag(R.drawable.blue_lays);
                break;
            case "red Lays":
                ImageList.add(R.drawable.blue_lays);
                ImageList.add(R.drawable.red_lays);
                ImageList.add(R.drawable.lays_green);
                ImageList.add(R.drawable.lays_green_ii);
                ImageList.add(R.drawable.lays_orangs);
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.red_lays));
                imageView.setTag(R.drawable.red_lays);
                break;
            case "green Lays":
                ImageList.add(R.drawable.blue_lays);
                ImageList.add(R.drawable.red_lays);
                ImageList.add(R.drawable.lays_green);
                ImageList.add(R.drawable.lays_green_ii);
                ImageList.add(R.drawable.lays_orangs);
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.lays_green));
                imageView.setTag(R.drawable.lays_green);
                break;
            case "green__ii Lays":
                ImageList.add(R.drawable.blue_lays);
                ImageList.add(R.drawable.red_lays);
                ImageList.add(R.drawable.lays_green);
                ImageList.add(R.drawable.lays_green_ii);
                ImageList.add(R.drawable.lays_orangs);
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.lays_green_ii));
                imageView.setTag(R.drawable.lays_green_ii);
                break;
            case "orange Lays":
                ImageList.add(R.drawable.blue_lays);
                ImageList.add(R.drawable.red_lays);
                ImageList.add(R.drawable.lays_green);
                ImageList.add(R.drawable.lays_green_ii);
                ImageList.add(R.drawable.lays_orangs);
                imageView.setTag(R.drawable.lays_orangs);
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.lays_orangs));
                break;
            case "chings hot garlic":
                ImageList.add(R.drawable.chings_hot_garlic);
                ImageList.add(R.drawable.chings_manchurian);
                ImageList.add(R.drawable.chings_singapur);
                imageView.setTag(R.drawable.chings_hot_garlic);
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.chings_hot_garlic));
                break;
            case "chings manchurian":
                ImageList.add(R.drawable.chings_hot_garlic);
                ImageList.add(R.drawable.chings_manchurian);
                ImageList.add(R.drawable.chings_singapur);
                imageView.setTag(R.drawable.chings_manchurian);
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.chings_manchurian));
                break;
            case "chings Singapore":
                ImageList.add(R.drawable.chings_hot_garlic);
                ImageList.add(R.drawable.chings_manchurian);
                ImageList.add(R.drawable.chings_singapur);
                imageView.setTag(R.drawable.chings_singapur);
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.chings_singapur));
                break;

            case "maggi cup noodles":
                ImageList.add(R.drawable.cup_maggi);
                ImageList.add(R.drawable.cup_tomato);
                ImageList.add(R.drawable.cup_chicken);
                ImageList.add(R.drawable.cup_italian);
                ImageList.add(R.drawable.cup_masala);
                imageView.setTag(R.drawable.maggi);
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.cup_maggi));
                break;

            case "Dark":
                imageView.setTag(R.drawable.dark_fantasy);
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.dark_fantasy));
                break;

            case "Hide Seek":
                imageView.setTag(R.drawable.hide_seek);
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.hide_seek));
                break;

            case "Instant Bhel":
                imageView.setTag(R.drawable.instant_bhel);
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.instant_bhel));
                break;

            case "Jim Jam":
                imageView.setTag(R.drawable.jim_jam);
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.jim_jam));
                break;

            case "kurkure chilli chatka":
                imageView.setTag(R.drawable.kur_chilli_chatka);
                ImageList.add(R.drawable.kur_chilli_chatka);
                ImageList.add(R.drawable.kur_green_chatney);
                ImageList.add(R.drawable.kurkure);
                ImageList.add(R.drawable.puffcorn);
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.kur_chilli_chatka));
                break;
            case "kurkure green":
                ImageList.add(R.drawable.kur_chilli_chatka);
                ImageList.add(R.drawable.kur_green_chatney);
                ImageList.add(R.drawable.kurkure);
                ImageList.add(R.drawable.puffcorn);
                imageView.setTag(R.drawable.kur_green_chatney);
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.kur_green_chatney));
                break;
            case "kurkure":
                ImageList.add(R.drawable.kur_chilli_chatka);
                ImageList.add(R.drawable.kur_green_chatney);
                ImageList.add(R.drawable.kurkure);
                ImageList.add(R.drawable.puffcorn);
                imageView.setTag(R.drawable.kurkure);
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.kurkure));
                break;
            case "kurkure puffcorn":
                ImageList.add(R.drawable.kur_chilli_chatka);
                ImageList.add(R.drawable.kur_green_chatney);
                ImageList.add(R.drawable.kurkure);
                ImageList.add(R.drawable.puffcorn);
                imageView.setTag(R.drawable.puffcorn);
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.puffcorn));
                break;

            case "maggi":
                ImageList.add(R.drawable.maggi);
                ImageList.add(R.drawable.maggi_hot_red);
                imageView.setTag(R.drawable.maggi);
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.maggi));
                break;
            case "maggi red":
                ImageList.add(R.drawable.maggi);
                ImageList.add(R.drawable.maggi_hot_red);
                imageView.setTag(R.drawable.maggi_hot_red);
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.maggi_hot_red));
                break;
            case "milk bikis cream":
                imageView.setTag(R.drawable.milk_bikis);
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.milk_bikis));
                break;
            case "nescafe":
                imageView.setTag(R.drawable.nescafe_25);
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.nescafe_25));
                break;
            case "oreo":
                imageView.setTag(R.drawable.oreo);
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.oreo));
                break;
            case "fab":
                imageView.setTag(R.drawable.fab);
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.fab));
                break;
            case "bourbon":
                imageView.setTag(R.drawable.bourborn);
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.bourborn));
                break;
            case "cheetos":
                imageView.setTag(R.drawable.cheetos);
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.cheetos));
                break;
            case "Cigarette":
                imageView.setTag(R.drawable.cigar);
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.cigar));
                break;
            case "marijuana":
                imageView.setTag(R.drawable.marijuana);
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.marijuana));
                break;
            case "cup_masala":
                ImageList.add(R.drawable.cup_maggi);
                ImageList.add(R.drawable.cup_tomato);
                ImageList.add(R.drawable.cup_chicken);
                ImageList.add(R.drawable.cup_italian);
                ImageList.add(R.drawable.cup_masala);
                imageView.setTag(R.drawable.cup_masala);
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.cup_masala));
                break;

            case "cup_chicken":
                imageView.setTag(R.drawable.cup_chicken);
                ImageList.add(R.drawable.cup_maggi);
                ImageList.add(R.drawable.cup_tomato);
                ImageList.add(R.drawable.cup_chicken);
                ImageList.add(R.drawable.cup_italian);
                ImageList.add(R.drawable.cup_masala);
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.cup_chicken));
                break;
            case "cup_italian":
                imageView.setTag(R.drawable.cup_italian);
                ImageList.add(R.drawable.cup_maggi);
                ImageList.add(R.drawable.cup_tomato);
                ImageList.add(R.drawable.cup_chicken);
                ImageList.add(R.drawable.cup_italian);
                ImageList.add(R.drawable.cup_masala);
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.cup_italian));
                break;
            case "cup_tomato":
                imageView.setTag(R.drawable.cup_tomato);
                ImageList.add(R.drawable.cup_maggi);
                ImageList.add(R.drawable.cup_tomato);
                ImageList.add(R.drawable.cup_chicken);
                ImageList.add(R.drawable.cup_italian);
                ImageList.add(R.drawable.cup_masala);
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.cup_tomato));
                break;
            case "windows":
                imageView.setTag(R.drawable.windows);
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.windows));
                break;
            case "linux":
                imageView.setTag(R.drawable.linux);
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.linux));
                break;
            case "wiwi":
                imageView.setTag(R.drawable.wiiw);
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.wiiw));
                break;
            case "marigold":
                imageView.setTag(R.drawable.marigold);
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.marigold));
                break;

        }
    }

    public ArrayList<Integer> getImageList(){
        return ImageList;
    }

    String getProduct(String name) {
        if ((name.toUpperCase().equals("Haldiram Bhujia sev".toUpperCase())) || (name.toUpperCase().equals("Haldiram".toUpperCase())) || name.toUpperCase().equals("Bhujia sev".toUpperCase()) || name.toUpperCase().equals("Haldiram Bhujia".toUpperCase()) || name.toUpperCase().equals("Bhujia".toUpperCase()) || name.toUpperCase().equals("Haldiram sev".toUpperCase())) {
            return "Haldiram Bhaujia Sev";
        }
        if ( (name.toUpperCase().equals("LAYS BLUE".toUpperCase())) || (name.toUpperCase().equals("BLUE Lays".toUpperCase())) || (name.toUpperCase().equals("Lays Blue".toUpperCase())) || (name.toUpperCase().equals("LAY'S BLUE")) || (name.toUpperCase().equals("LAY'S India's BLUE".toUpperCase())) || (name.toUpperCase().equals("LAYs Magic Masala".toUpperCase())) || (name.toUpperCase().equals("Lay's Magic Masala".toUpperCase()))  || (name.toUpperCase().equals("LAY'S India's magic Masala".toUpperCase()))  ){
            return "Blue Lays";
        }
        if ( (name.toUpperCase().equals("LAYS Green".toUpperCase())) || (name.toUpperCase().equals("Green Lays".toUpperCase())) || (name.toUpperCase().equals("LAY'S")) || (name.toUpperCase().equals("LAY'S American")) || (name.toUpperCase().equals("LAY'S onion".toUpperCase())) || (name.toUpperCase().equals("LAYs onion".toUpperCase())) || (name.toUpperCase().equals("Lay's American style cream & onion".toUpperCase()))   ){
            return "green Lays";
        }
        if ( (name.toUpperCase().equals("LAYS red".toUpperCase())) || (name.toUpperCase().equals("LAY'S RED")) || (name.toUpperCase().equals("LAY'S Spanish")) || (name.toUpperCase().equals("LAY'S Tomato".toUpperCase())) || (name.toUpperCase().equals("LAYs Tomato Tango".toUpperCase())) || (name.toUpperCase().equals("Lay's Spanish Tomato Tango".toUpperCase())) || (name.toUpperCase().equals("Lay's Tomato Tango".toUpperCase())) || (name.toUpperCase().equals("Lays".toUpperCase()))  ){
            return "red Lays";
        }
        if ( (name.toUpperCase().equals("LAYS light green".toUpperCase())) || (name.toUpperCase().equals("LAY'S sour ceam & onion")) || (name.toUpperCase().equals("LAY'S sour cream and onion")) || (name.toUpperCase().equals("LAY'S sour cream onion".toUpperCase()))  ){
            return "green_ii Lays";
        }
        if ( (name.toUpperCase().equals("LAYS orange".toUpperCase())) || (name.toUpperCase().equals("LAY'S orange")) || (name.toUpperCase().equals("LAY'S orange")) || (name.toUpperCase().equals("LAY'S orange".toUpperCase())) || (name.toUpperCase().equals("LAYs west indies".toUpperCase())) || (name.toUpperCase().equals("Lay's west indies".toUpperCase())) || (name.toUpperCase().equals("Lay's west indies hot n sweet chili".toUpperCase())) || (name.toUpperCase().equals("Lays hot n serrt chili".toUpperCase()))  ){
            return "orange Lays";
        }
        if ( (name.toUpperCase().equals("Hot Garlic".toUpperCase())) || (name.toUpperCase().equals("Chings Hot Garlic".toUpperCase())) || (name.toUpperCase().equals("Ching's Hot Garlic".toUpperCase()))){
            return "chings hot garlic";
        }
        if ( (name.toUpperCase().equals("Manchurian".toUpperCase())) || (name.toUpperCase().equals("Chings manchurian".toUpperCase())) || (name.toUpperCase().equals("Ching's manchurian".toUpperCase()))){
            return "chings manchurian";
        }
        if ( (name.toUpperCase().equals("Singapore".toUpperCase())) || (name.toUpperCase().equals("Chings Singapore".toUpperCase())) || (name.toUpperCase().equals("Ching's Singapore".toUpperCase()))){
            return "chings Singapore";
        }
        if ( (name.toUpperCase().equals("Maggi cuppa mania".toUpperCase())) || (name.toUpperCase().equals("Magi cuppa mania".toUpperCase())) || (name.toUpperCase().equals("Magi cup".toUpperCase())) || (name.toUpperCase().equals("Maggi cup".toUpperCase())) || (name.toUpperCase().equals("Maggi Cup noodles".toUpperCase())) || (name.toUpperCase().equals("Maggi insta-Noodle".toUpperCase()))){
            return "maggi cup noodles";
        }
        if ( (name.toUpperCase().equals("Dark Fantasy".toUpperCase())) || (name.toUpperCase().equals("Fantasy".toUpperCase())) || (name.toUpperCase().equals("Dark Fantasy choco fills".toUpperCase()))){
            return "Dark";
        }
        if ( (name.toUpperCase().equals("Hide and Seek".toUpperCase())) || (name.toUpperCase().equals("Hide Seek".toUpperCase())) || (name.toUpperCase().equals("Hide & Seek".toUpperCase()))){
            return "Hide Seek";
        }
        if ( (name.toUpperCase().equals("Instant Bhel".toUpperCase())) || (name.toUpperCase().equals("Instant Bhel".toUpperCase())) || (name.toUpperCase().equals("Haldiram Instant Bhel".toUpperCase())) || (name.toUpperCase().equals("Haldiram Bhel".toUpperCase()))){
            return "Instant Bhel";
        }
        if ( (name.toUpperCase().equals("Jim jam".toUpperCase())) || (name.toUpperCase().equals("Jim and Jam".toUpperCase())) || (name.toUpperCase().equals("JimJam".toUpperCase()))){
            return "Jim Jam";
        }
        if ( (name.toUpperCase().equals("kurkure chilli chatka".toUpperCase())) || (name.toUpperCase().equals("kurkure chili chatka".toUpperCase())) || (name.toUpperCase().equals("Kurkure chilli".toUpperCase())) || (name.toUpperCase().equals("kurkure dark green".toUpperCase()))){
            return "kurkure chilli chatka";
        }
        if ( (name.toUpperCase().equals("kurkure green chutney".toUpperCase())) || (name.toUpperCase().equals("kurkure green chatney".toUpperCase())) || (name.toUpperCase().equals("Kurkure green".toUpperCase())) || (name.toUpperCase().equals("kurkure chutney".toUpperCase()))){
            return "kurkure green";
        }
        if ( (name.toUpperCase().equals("kurkure masala munch".toUpperCase())) || (name.toUpperCase().equals("Kurkure".toUpperCase())) || (name.toUpperCase().equals("kurkure masala".toUpperCase()))){
            return "kurkure";
        }
        if ( (name.toUpperCase().equals("kurkure puffcorn".toUpperCase())) || (name.toUpperCase().equals("Kurkure puffcorn yummy cheese".toUpperCase())) || (name.toUpperCase().equals("puffcorn".toUpperCase()))){
            return "kurkure puffcorn";
        }
        if ( (name.toUpperCase().equals("maggi".toUpperCase())) || (name.toUpperCase().equals("maggi 12".toUpperCase())) || (name.toUpperCase().equals("maggi 24".toUpperCase()))){
            return "maggi";
        }
        if ( (name.toUpperCase().equals("hot heads".toUpperCase())) || (name.toUpperCase().equals("maggi hot heads".toUpperCase())) || (name.toUpperCase().equals("maggi red".toUpperCase()))){
            return "maggi red";
        }
        if ( (name.toUpperCase().equals("milk bikis".toUpperCase())) || (name.toUpperCase().equals("milk bikis milk cream".toUpperCase())) || (name.toUpperCase().equals("milk bikis cream".toUpperCase()))){
            return "milk bikis cream";
        }
        if ( (name.toUpperCase().equals("nescafe".toUpperCase()))){
            return "nescafe";
        }
        if ( (name.toUpperCase().equals("oreo".toUpperCase()))){
            return "oreo";
        }
        if ( (name.toUpperCase().equals("fab".toUpperCase()))){
            return "fab";
        }
        if ( (name.toUpperCase().equals("cheetos".toUpperCase()))){
            return "cheetos";
        }
        if ( (name.toUpperCase().equals("bourbon".toUpperCase()))){
            return "bourbon";
        }
        if ( (name.toUpperCase().equals("Cigarette".toUpperCase())) || (name.toUpperCase().equals("cigaratte".toUpperCase())) || (name.toUpperCase().equals("Cigaratte".toUpperCase())) ) {
            return "Cigarette";
        }
        if ( (name.toUpperCase().equals("drugs".toUpperCase())) || (name.toUpperCase().equals("weed".toUpperCase())) || (name.toUpperCase().equals("weeds".toUpperCase())) || (name.toUpperCase().equals("meth".toUpperCase())) || (name.toUpperCase().equals("marijuana".toUpperCase())) || (name.toUpperCase().equals("green".toUpperCase())) ) {
            return "marijuana";
        }
        if ( (name.toUpperCase().equals("cup noodles".toUpperCase())) || (name.toUpperCase().equals("cup masala".toUpperCase())) || (name.toUpperCase().equals("masala cup noodles".toUpperCase())) ) {
            return "cup_masala";
        }
        if ( (name.toUpperCase().equals("cup noodles chicken".toUpperCase())) || (name.toUpperCase().equals("cup chicken".toUpperCase())) || (name.toUpperCase().equals("chicken cup noodles".toUpperCase())) ) {
            return "cup_chicken";
        }
        if ( (name.toUpperCase().equals("cup noodles italian".toUpperCase())) || (name.toUpperCase().equals("cup italian".toUpperCase())) || (name.toUpperCase().equals("italian cup noodles".toUpperCase())) ) {
            return "cup_italian";
        }
        if ( (name.toUpperCase().equals("cup noodles tomato".toUpperCase())) || (name.toUpperCase().equals("cup tomato".toUpperCase())) || (name.toUpperCase().equals("tomato cup noodles".toUpperCase())) ) {
            return "cup_tomato";
        }
        if ( (name.toUpperCase().equals("windows".toUpperCase())) ) {
            return "windows";
        }
        if ( (name.toUpperCase().equals("linux".toUpperCase())) || (name.toUpperCase().equals("kali".toUpperCase())) || (name.toUpperCase().equals("ubuntu".toUpperCase())) ) {
            return "linux";
        }
        if ( (name.toUpperCase().equals("wiwi".toUpperCase())) || (name.toUpperCase().equals("waiwai".toUpperCase())) || (name.toUpperCase().equals("wiawia".toUpperCase())) ) {
            return "wiwi";
        }
        if ( (name.toUpperCase().equals("marigold".toUpperCase())) || (name.toUpperCase().equals("mari gold".toUpperCase())) ) {
            return "marigold";
        }


        return null;
    }
}
