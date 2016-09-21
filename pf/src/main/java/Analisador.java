import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

public class Analisador {
	
	 public static void main(String[] args) throws IOException, InvalidRefNameException, GitAPIException {
	        try (Repository repository = CookbookHelper.openJGitCookbookRepository()) {
	            try (Git git = new Git(repository)) {
	                Iterable<RevCommit> commits = git.log().all().call();
	                int count = 0;
	                for (RevCommit commit : commits) {
	                    System.out.println("LogCommit: " + commit);
	                    count++;
	                }
	                System.out.println(count);
	            }
	        }
	    }

	   
}
	
