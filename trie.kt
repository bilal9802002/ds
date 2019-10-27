import java.util.*

class Trie {
    private inner class TrieNode {
        val children = HashMap<Char, TrieNode>()
        var endOfWord: Boolean = false
    }

    private val rootNode: TrieNode = TrieNode()
    
    fun size(): Int {
        return wordsCount(rootNode)
    }
    
    fun insert(word: String) {
        if (word.isEmpty())
        	return
        
        var current = rootNode
        
        word.forEach {
            ch -> current = current.children.computeIfAbsent(ch) {
                _ -> TrieNode()
            }

        }
        
        current.endOfWord = true
    }
    
    fun delete(word: String): Boolean {

        var current = rootNode
        
        val stack = Stack<Pair<Char, TrieNode>>()
        
        for (i in 0..word.length - 1) {
            if (!current.children.contains(word[i]))
            	return false
            
            stack.push(Pair(word[i], current))
            current = current.children[word[i]]!!
        }
        
        if (!current.endOfWord)
        	return false
        
        current.endOfWord = false
        
        if (current.children.isEmpty()) {
            while(stack.isNotEmpty()) {
                val p = stack.pop()
                
                if (p.second.endOfWord)
                   break
                
                p.second.children.remove(p.first)
            }
        }
       
        return true
    }
    
    fun getWords(): ArrayList<String> {
        return words(rootNode, "", arrayListOf<String>())
    }
    
    fun getWords(prefix: String): ArrayList<String> {
        val words = arrayListOf<String>()
        
        if (prefix.isNotEmpty()) {
            var current = rootNode
            
	        for (i in 0..prefix.length - 1) {
    	        if (!current.children.contains(prefix[i])) {
                    return words
            	}
            
	            current = current.children[prefix[i]]!!
    	    }
            
            if (current.endOfWord)
            	words.add(prefix)
                
            words(current, prefix, words)
        }
        
        return words
    }
    
    fun contains(word: String): Boolean {       
        var current = rootNode
        
        for (i in 0..word.length - 1) {
            if (!current.children.contains(word[i])) {
                return false
            }
            
            current = current.children[word[i]]!!
        }
        
        return current.endOfWord
    }
    
    fun dump(): String {
        return _dump(rootNode.children)
    }
    
    private fun _dump(children: HashMap<Char, TrieNode>): String {
        if (children.isEmpty())
        	return ""
        
        var s = ""
        
        for (child in children) {
            s += child.key + _dump(child.value.children)
        }
        
        return s
    }
     
    private fun words(node: TrieNode, prefix: String, words: ArrayList<String>): ArrayList<String> {
        for(child in node.children) {
            val nstr = prefix + child.key
            
            if (child.value.endOfWord)
               words.add(nstr)
               
            words(child.value, nstr, words)
        }
        
        return words
    }
    
    private fun wordsCount(node: TrieNode): Int {
        var count = if (node.endOfWord) 1 else 0
        
        for (child in node.children) {
            count += wordsCount(child.value)
        }
        
        return count
    }
}


fun main() {
    val t = Trie()
    
    t.insert("box")
    t.insert("books")
    t.insert("bills")
    t.insert("maze")
    t.insert("made")
    t.insert("king")
    t.insert("kong")
    t.insert("kotlin")
    t.insert("cake")
    t.insert("cans")
    t.insert("can")
    t.insert("coke")
    t.insert("cane")
    t.insert("cook")
    t.insert("bird")
    t.insert("zigzag")
    
    println("dump: " + t.dump())

    println("Total words count: " + t.size())
    
    println("Contains word 'can': " + t.contains("can"))
   	println("Delete word 'can': " + t.delete("can"))
    println("Has word 'can'? " + t.contains("can"))
    
    println("dump: " + t.dump())
    println("Total words count: " + t.size())
    println("Print All words: " + t.getWords().toString())

    println("Print Words start with 'co': " + t.getWords("co").toString())
    println("Print Words start with 'ma': " + t.getWords("ma").toString())
    println("Print Words start with 'kotlin': " + t.getWords("kotlin").toString())            
}