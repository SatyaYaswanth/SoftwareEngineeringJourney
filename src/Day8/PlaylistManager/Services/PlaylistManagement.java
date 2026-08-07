package Day8.PlaylistManager.Services;

import Day8.PlaylistManager.Model.Song;

import java.util.LinkedList;
import java.util.List;

public class PlaylistManagement {

    LinkedList<Song> playlist = new LinkedList<>();
    int currentIndex = 0;

    public void menu(){
        System.out.println("====Playlist Management====");
        System.out.println("1.Add First.");
        System.out.println("2.Add Last.");
        System.out.println("3.Remove First.");
        System.out.println("4.Remove Last.");
        System.out.println("5.Display playlist.");
        System.out.println("6.Play Current Song.");
        System.out.println("7.Play Next Song.");
        System.out.println("8.Play Previous Song.");
        System.out.println("9.Exit");
    }

    public void addFirst(Song song){
        playlist.addFirst(song);
    }

    public void addLast(Song song){
        playlist.addLast(song);
    }

    public void removeFirst(){
        if(playlist.isEmpty()){
            System.out.println("Playlist is empty.");
            return;
        }
        playlist.removeFirst();
    }

    public void removeLast(){
        if(playlist.isEmpty()){
            System.out.println("Playlist is empty.");
            return;
        }
        playlist.removeLast();
    }

    public void displayPlaylist(){
        for(int i=0;i<playlist.size();i++){
            if(i==currentIndex)
                System.out.print("▶ ");
            else
                System.out.print("  ");
            System.out.println(playlist.get(i));
        }
//        for(Song song : playlist){
//            System.out.println(song);
//        }
    }

    public void playCurrentSong(){
        if(playlist.isEmpty()){
            System.out.println("Playlist is empty.");
            return;
        }
        System.out.println("Playing current song : " + playlist.get(currentIndex));
    }

    public void playNextSong(){
        if(playlist.isEmpty()){
            System.out.println("Playlist is empty.");
            return;
        }
        if(currentIndex < playlist.size()-1){
            currentIndex++;
            System.out.println("Playing current song : " + playlist.get(currentIndex));
        }
        else{
            System.out.println("Already at last song.");
        }
    }

    public void playPrevSong(){
        if(playlist.isEmpty()){
            System.out.println("Playlist is empty.");
            return;
        }
        if(currentIndex > 0){
            currentIndex--;
            System.out.println("Playing current song : " + playlist.get(currentIndex));
        }
        else{
            System.out.println("Already at last first song.");
        }
    }

    public void removeCurrentSong(){
        if(playlist.isEmpty()){
            System.out.println("Playlist is empty.");
            return;
        }
        System.out.println(playlist.get(currentIndex) + " removed" + playlist.remove(currentIndex));
    }

}
