package Day8.PlaylistManager;

import Day8.PlaylistManager.Model.Song;
import Day8.PlaylistManager.Services.PlaylistManagement;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static Song createSong(Scanner sc){
        System.out.print("ID : ");
        int id = sc.nextInt();
        System.out.print("Title : ");
        String title = sc.next();
        System.out.print("Artist : ");
        String artist = sc.next();
        return new Song(id,title,artist);
    }

    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);
        PlaylistManagement service = new PlaylistManagement();
        boolean isRunning = true;
        int choice;

        while(isRunning){
            service.menu();
            System.out.print("Enter your choice : ");
            choice=sc.nextInt();
            int sid;
            String title;
            String artist;

            switch (choice){
                case 1:
                    service.addFirst(createSong(sc));
                    break;
                case 2:
                    service.addLast(createSong(sc));
                    break;
                case 3:
                    service.removeFirst();
                    break;
                case 4:
                    service.removeLast();
                    break;
                case 5:
                    service.displayPlaylist();
                    break;
                case 6:
                    service.playCurrentSong();
                    break;
                case 7:
                    service.playNextSong();
                    break;
                case 8:
                    service.playPrevSong();
                    break;
                case 9:
                    isRunning=false;
                    break;
                default:
                    System.out.println("Invalid input.");
            }
        }



    }
}
