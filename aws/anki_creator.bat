ls -1 *anki.md* 
rm aws_merged_anki.md
cat *anki*.md >> aws_merged_ikna.md
mv aws_merged_ikna.md aws_merged_anki.md
mdanki aws_merged_anki.md aws_merged_anki.apkg --deck "AWS SAA Certification::MdAnki::SAA-CA02"
pandoc aws_merged_anki.md --pdf-engine "D:\Apps\pandoc-2.10.1\mitex\miktex\bin\x64\pdflatex.exe" -o aws.pdf