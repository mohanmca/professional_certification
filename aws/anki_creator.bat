ls -1 *anki.md* 
rm aws_merged_anki.md
cat *anki*.md >> aws_merged_ikna.md
mv aws_merged_ikna.md aws_merged_anki.md
mdanki aws_merged_anki.md aws_merged_anki.apkg --deck "AWS SAA Certification::MdAnki::SAA-CA02"